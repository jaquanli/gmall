package com.atguigu.gmall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.cart.mapper.CartInfoMapper;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.utils.CartUtil;
import com.atguigu.gmall.utils.RedisUtil;
import org.apache.ibatis.annotations.Flush;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    //定义user对应的购物车在缓存中的key
    private String userCartKey;

    @Autowired
    private CartInfoMapper cartInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void addToCartFromDB(String userId, SkuInfo skuInfo, String skuNum) {
        //获取redis连接
        Jedis jedis = redisUtil.getJedis();
        //定义user对应的购物车在缓存中的key
        userCartKey = "user:" + userId + ":cart";

        Example exampleSelect = new Example(CartInfo.class);
        exampleSelect.createCriteria().andEqualTo("skuId", skuInfo.getId()).andEqualTo("userId", Long.parseLong(userId));
        CartInfo cartInfoDB = cartInfoMapper.selectOneByExample(exampleSelect);

        if (cartInfoDB == null) {
            //通过userId和skuId未查询到说明，数据库中没有该购物车记录，就进行添加
            CartInfo cartInfoInsert = new CartInfo();
            cartInfoInsert.setUserId(Long.parseLong(userId));
            cartInfoInsert.setSkuId(skuInfo.getId());
            cartInfoInsert.setCartPrice(skuInfo.getPrice().multiply(new BigDecimal(skuNum)));
            cartInfoInsert.setImgUrl(skuInfo.getSkuDefaultImg());
            cartInfoInsert.setIsChecked("1");
            cartInfoInsert.setSkuPrice(skuInfo.getPrice());
            cartInfoInsert.setSkuNum(Integer.parseInt(skuNum));
            cartInfoInsert.setSkuName(skuInfo.getSkuName());

            cartInfoMapper.insertSelective(cartInfoInsert);
            cartInfoInsert.setId(cartInfoInsert.getId());
            String cartInfoJSON = JSON.toJSONString(cartInfoInsert);
            //更新缓存
            jedis.hset(userCartKey, String.valueOf(skuInfo.getId()), cartInfoJSON);
            //关闭redis连接
            jedis.close();

        } else {
            //查询到结果，就进行更新
            //更新SkuNum
            Integer cartSkuNum = cartInfoDB.getSkuNum() + Integer.parseInt(skuNum);
            //更新cartPrice
            BigDecimal cartPrice = cartInfoDB.getSkuPrice().multiply(new BigDecimal(String.valueOf(cartSkuNum)));
            Example exampleUpdate = new Example(CartInfo.class);
            exampleUpdate.createCriteria()
                    .andEqualTo("userId", userId)
                    .andEqualTo("skuId", skuInfo.getId());
            cartInfoDB.setSkuNum(cartSkuNum);
            cartInfoDB.setCartPrice(cartPrice);
            //根据exampleUpdate条件查询出，并根据cartInfoDB条件进行更新
            cartInfoMapper.updateByExampleSelective(cartInfoDB, exampleUpdate);
            //更新缓存
            flushCacheFormCartInfo(Long.parseLong(userId));
        }
    }

    @Override
    public List<CartInfo> getCartListFromCache(String userId) {
        //获取redis连接
        Jedis jedis = redisUtil.getJedis();
        //key
        userCartKey = "user:" + userId + ":cart";
        //封装cartInfo的集合对象
        List<CartInfo> cartInfoList = new ArrayList<>();
        //首先去redis根据key去获取数据
        List<String> cartInfoListJsonList = jedis.hvals(userCartKey);
        //如果redis没有获取到数据，去DB查
        if (cartInfoListJsonList == null) {
            Example example = new Example(CartInfo.class);
            example.createCriteria().andEqualTo("userId", userId);
            List<CartInfo> cartInfoListDB = cartInfoMapper.selectByExample(example);
            //保证查到有数据
            if (cartInfoListDB != null) {
                //同步redis
                flushCacheFormCartInfo(Long.parseLong(userId));
            }
        } else {
            //redis有数据
            //进行反序列化
            for (String cartInfoJson : cartInfoListJsonList) {
                CartInfo cartInfo = JSON.parseObject(cartInfoJson, CartInfo.class);
                cartInfoList.add(cartInfo);
            }
            //排序
            cartInfoList.sort(new Comparator<CartInfo>() {
                @Override
                public int compare(CartInfo o1, CartInfo o2) {
                    return Long.compare(o2.getId(), o1.getId());
                }
            });
        }
        return cartInfoList;
    }

    @Override
    public List<CartInfo> updateCartCheck(String isChecked, String skuId, String userId) {


        List<CartInfo> cartInfoList = new ArrayList<>();
        Jedis jedis = redisUtil.getJedis();
        userCartKey = "user:" + userId + ":cart";
        //1.先去缓存中获取数据
        List<String> CartListJedis = jedis.hvals(userCartKey);
        //遍历出
        for (String cartListJedi : CartListJedis) {
            //反序列化
            CartInfo cartInfo = JSON.parseObject(cartListJedi, CartInfo.class);
            // 2.更新缓存中的数据
            if (cartInfo.getSkuId().equals(Long.parseLong(skuId))) {
                cartInfo.setIsChecked(isChecked);
                //3.更新数据库
                Example exampleUpdate = new Example(CartInfo.class);
                exampleUpdate.createCriteria()
                        .andEqualTo("userId", userId)
                        .andEqualTo("skuId", skuId);
                cartInfoMapper.updateByExampleSelective(cartInfo, exampleUpdate);
            }
            cartInfoList.add(cartInfo);
        }
        //刷新缓存
        flushCacheFormCartInfo(Long.parseLong(userId));
        //关闭释放连接
        jedis.close();
        //返回
        return cartInfoList;
    }

    @Override
    public Boolean mergeCart(Long userId, List<CartInfo> cartInfoListCookie) {

        try {
            Example example = new Example(CartInfo.class);
            example.createCriteria().andEqualTo("userId", userId);
            List<CartInfo> cartInfoListDB = cartInfoMapper.selectByExample(example);
//            boolean contains = cartInfoListDB.contains(cartInfoListCookie);
//            if (contains){
//                for (CartInfo cartInfoCookie : cartInfoListCookie) {
//                    for (CartInfo cartInfoDB : cartInfoListDB) {
//                        //更新skuNum和cartPrice和勾选状态
//                        cartInfoDB.setSkuNum(cartInfoDB.getSkuNum() + cartInfoCookie.getSkuNum());
//                        cartInfoDB.setCartPrice(cartInfoDB.getSkuPrice().multiply(new BigDecimal(String.valueOf(cartInfoDB.getSkuNum()))));
//                        cartInfoDB.setIsChecked(cartInfoCookie.getIsChecked());
//                        cartInfoMapper.updateByPrimaryKeySelective(cartInfoDB);
//                    }
//                    cartInfoCookie.setUserId(userId);
//                }
//            }else {
//                cartInfoMapper.insertSelective(cartInfoCookie);
//            }
            //合并数据库


            if (cartInfoListDB != null && cartInfoListCookie != null && cartInfoListDB.size() > 0 && cartInfoListCookie.size() > 0) {
                for (CartInfo cartInfoCookie : cartInfoListCookie) {
                    //判断在更新时是否存在于之前的Cookie，是否为一个新的购物车
                    Boolean ifNewCart = CartUtil.ifNewCart(cartInfoListDB, cartInfoCookie);
                    if (ifNewCart){
                        //新增
                        cartInfoCookie.setUserId(userId);
                        cartInfoMapper.insertSelective(cartInfoCookie);
                    }else {
                        //更新
                        Long skuIdCookie = cartInfoCookie.getSkuId();
                        for (CartInfo cartInfoDB : cartInfoListDB) {
                            if (skuIdCookie.equals(cartInfoDB.getSkuId())) {
                                //更新skuNum和cartPrice和勾选状态
                                cartInfoDB.setSkuNum(cartInfoDB.getSkuNum() + cartInfoCookie.getSkuNum());
                                cartInfoDB.setCartPrice(cartInfoDB.getSkuPrice().multiply(new BigDecimal(String.valueOf(cartInfoDB.getSkuNum()))));
                                cartInfoDB.setIsChecked(cartInfoCookie.getIsChecked());
                                cartInfoMapper.updateByPrimaryKeySelective(cartInfoDB);
                            }
                        }
                    }
                }
            }else if (cartInfoListCookie != null && cartInfoListCookie.size() > 0){
                for (CartInfo cartInfoCookie : cartInfoListCookie) {
                    cartInfoCookie.setUserId(userId);
                    cartInfoMapper.insertSelective(cartInfoCookie);
                }
            }

            //同步缓存
            flushCacheFormCartInfo(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 刷新同步redis
     *
     * @param userId 传入需要进行查询数据库，根据用户id进行同步缓存
     */
    public void flushCacheFormCartInfo(Long userId) {
        //定义key
        userCartKey = "user:" + userId + ":cart";
        //获取redis连接
        Jedis jedis = redisUtil.getJedis();
        //创建查询条件
        Example example = new Example(CartInfo.class);
        example.createCriteria().andEqualTo("userId", userId);
        //查询
        List<CartInfo> cartInfoListDB = cartInfoMapper.selectByExample(example);
        if (cartInfoListDB != null) {
            //同步之前要删除之前的旧数据
            jedis.del(userCartKey);
            //创建Map方便同步redis
            Map<String, String> cartInfoListMap = new HashMap<>();
            //遍历查出的值，并放入map中
            for (CartInfo cartInfo : cartInfoListDB) {
                cartInfoListMap.put(String.valueOf(cartInfo.getId()), JSON.toJSONString(cartInfo));
            }
            //同步redis
            jedis.hmset(userCartKey, cartInfoListMap);
            //关闭
            jedis.close();
        }

    }

    /**
     * 获得数据库中的强一致性数据
     * @param userId 用户id，查询条件
     * @return 购物车信息
     */
    @Override
    public List<CartInfo> queryCartListFormDBByUserId(String userId) {

        Example example = new Example(CartInfo.class);
        example.createCriteria().andEqualTo("userId",Long.parseLong(userId));

        return cartInfoMapper.selectByExample(example);
    }

    @Override
    public void removeCheckedCart(String userId, List<Long> checkedCartIdList) {
        //删除数据库记录
        int count = cartInfoMapper.deleteCartInfoByIds(checkedCartIdList);
        if (count >=0 ){
            //刷新缓存
            flushCacheFormCartInfo(Long.parseLong(userId));
        }


    }

}

