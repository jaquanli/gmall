package com.atguigu.gmall.user.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.UserInfoService;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> queryUserInfoAll() {

        return userInfoMapper.selectAll();
    }

    @Override
    public UserInfo queryUserInfoById(Integer id) {

        return userInfoMapper.selectByPrimaryKey(id);
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
}
