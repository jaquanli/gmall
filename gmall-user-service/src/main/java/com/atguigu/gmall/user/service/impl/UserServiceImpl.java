package com.atguigu.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.consts.WebConst;
import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.user.mapper.UserAddressMapper;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import com.atguigu.gmall.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserInfo> queryUserInfoAll() {

        return userInfoMapper.selectAll();
    }

    @Override
    public UserInfo queryUserInfoById(String id) {

        return userInfoMapper.selectByPrimaryKey(Long.parseLong(id));
    }

    @Override
    public void updateUserInfoById(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    public void removeUserInfoById(UserInfo userInfo) {
        userInfoMapper.deleteByPrimaryKey(userInfo);
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }


    @Override
    public List<UserAddress> queryUserAddressList() {

        return userAddressMapper.selectAll();
    }

    @Override
    public UserAddress queryUserAddressById(String id) {

        return userAddressMapper.selectByPrimaryKey(Long.parseLong(id));
    }

    @Override
    public void updateUserAddressById(UserAddress userAddress) {
        userAddressMapper.updateByPrimaryKey(userAddress);
    }

    @Override
    public void removeUserAddressById(UserAddress userAddress) {

        userAddressMapper.delete(userAddress);

    }

    @Override
    public void saveUserAddress(UserAddress userAddress) {
        userAddressMapper.insert(userAddress);
    }

    @Override
    public UserInfo queryUserInfoByLoginNameAndPasswd(String loginName, String passwd) {

        Jedis jedis = redisUtil.getJedis();
        String userInfoKeyByLoginName = "user:" + loginName + ":info";

        UserInfo userInfo = null;

        //先去redis查
        String userInfoByRedis = jedis.get(userInfoKeyByLoginName);
        //在查DB之前肯定要设置分布式锁，防止高并发时的缓存击穿
        if (StringUtils.isBlank(userInfoByRedis)){
            //查不到去DB中查
            Example example = new Example(UserInfo.class);
            example.createCriteria().andEqualTo("loginName",loginName);
            example.createCriteria().andEqualTo("passwd",passwd);
            userInfo = userInfoMapper.selectOneByExample(example);
            if (userInfo != null) {
                //同步到redis并设置超时时间7天
                jedis.set(userInfoKeyByLoginName, JSON.toJSONString(userInfo));
                jedis.expire(userInfoKeyByLoginName, WebConst.USER_INFO_OUT_TIME);
                jedis.close();
            }else {
                //这里肯定也要设置一个空值标记的redis缓存数据，并设置过期时间，防止缓存穿透。
            }
        }else {
            //这里还要判断是否为一个空值标记的redis数据，如果有就设置为userInfo为null
            userInfo = JSON.parseObject(userInfoByRedis, UserInfo.class);
        }
        return userInfo;
    }

    @Override
    public List<UserAddress> queryUserAddressListByUserId(String userId) {

        //正规业务流程应该是先去redis去查数据，没有在去DB查数据，肯定要先设置分布式锁（肯定要数据库与缓存处理一致性的问题）
        //这里因为时间关系就简写

        Example example = new Example(UserAddress.class);
        example.createCriteria().andEqualTo("userId",Long.parseLong(userId));

        return userAddressMapper.selectByExample(example);
    }
}
