package com.atguigu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.SkuAttrValue;
import com.atguigu.gmall.bean.SkuImage;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.manager.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.manager.mapper.SkuImageMapper;
import com.atguigu.gmall.manager.mapper.SkuInfoMapper;
import com.atguigu.gmall.manager.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<SkuInfo> querySkuInfoListBySpuInfoId(String spuInfoId) {

        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(Long.parseLong(spuInfoId));

        return skuInfoMapper.select(skuInfo);
    }

    @Override
    public void saveSku(SkuInfo skuInfo) {

        if (skuInfo != null) {
            skuInfoMapper.insertSelective(skuInfo);
            Long skuId = skuInfo.getId();

            List<SkuImage> skuImageList = skuInfo.getSkuImageList();
            if (skuImageList != null) {
                for (SkuImage skuImage : skuImageList) {
                    skuImage.setSkuId(skuId);
                    skuImageMapper.insertSelective(skuImage);
                }
            }

            List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
            if (skuAttrValueList != null) {
                for (SkuAttrValue skuAttrValue : skuAttrValueList) {

                    skuAttrValue.setSkuId(skuId);
                    skuAttrValueMapper.insertSelective(skuAttrValue);

                }
            }

            List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            if (skuSaleAttrValueList != null) {
                for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                    skuSaleAttrValue.setSkuId(skuId);
                    skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
                }
            }
        }
    }

    /**
     * 从数据库中获取skuInfo的数据
     *
     * @param skuId 传入的id
     * @return SkuInfo
     */
    private SkuInfo querySkuInfoByIdFormByDB(String skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(Long.parseLong(skuId));
        if (skuInfo != null) {
            SkuImage skuImage = new SkuImage();
            skuImage.setSkuId(Long.parseLong(skuId));
            List<SkuImage> skuImageList = skuImageMapper.select(skuImage);
            skuInfo.setSkuImageList(skuImageList);
        }

        return skuInfo;
    }

    @Override
    public SkuInfo querySkuInfoById(String skuId) {

        SkuInfo skuInfo = null;

        try {
            //通过整合的Redis获取jedis连接
            Jedis jedis = redisUtil.getJedis();

            //设置查询Redis中skuInfo的key
            String skuInfoKey = "sku:" + skuId + ":skuInfo";

            String skuInfoLock = "sku:" + skuId + ":lock";

            if (jedis != null) {
                //首先去Redis中获取数据，根据key查询Redis的数据
                String skuInfoValue = jedis.get(skuInfoKey);

                //如果没有数据就去先解决缓存击穿在去DB中查询
                if (StringUtils.isBlank(skuInfoValue)) {
                    //设置超时时间3分钟
                    //int timeOut = 60*3;
                    //为避免缓存击穿，即当某一个key不存在时，就会立即访问BD，在高并发时DB会导致宕机
                    //jedis.setnx(skuInfoLock,skuInfoValue);
                    //setnx当key存在时设置失败返回0，不存在时设置成功返回1
                    Long setexStatus = jedis.setnx(skuInfoLock, skuInfoValue + "lock");
                   jedis.expire(skuInfoLock,20);
                    //设置成功时进行去数据库查询
                    if (setexStatus == 1) {
                        //通过方法获取到数据库中的数据
                        skuInfo = querySkuInfoByIdFormByDB(skuId);
                        //获取到数据库数据时
                        if (skuInfo != null) {
                            //当在DB查询到数据之后，首选要将数据转换的json字符串对象，以便保存到Redis
                            skuInfoValue = JSON.toJSONString(skuInfo);
                            //然后同步到Redis
                            jedis.set(skuInfoKey, skuInfoValue);
                            //删除锁操作，方便下一个线程进行使用
                            jedis.del(skuInfoLock);
                        } else {
                            /*
                              这里也可以采用BloomFilter布隆过滤器来解决，以后再研究
                             */
                            //未获取到数据库数据时，设置一个值为空值，并过期时间为3分钟
                            int timeOut = 60 * 1000 * 3;
                            jedis.set(skuInfoKey, "empty", "nx", "px", timeOut);
                            //删除锁操作，方便下一个线程进行使用
                            jedis.del(skuInfoLock);
                        }
                    } else {
                        //当其中一个获取锁失败时，即设置key失败时，线程sleep 5秒
                        Thread.sleep(5000);
                        //当时间过了，进入此地的线程进行再次调用方法，这种叫回旋
                        return querySkuInfoById(skuId);
                    }
                } else {
                    if (!skuInfoValue.equals("empty")){
                        //如果有数据就直接转换成json对象，返回
                        //使用fastJson将获取的key值转换成对象，需要传入转换成的实例的对象类型
                        skuInfo = JSON.parseObject(skuInfoValue, SkuInfo.class);

                    }
                }
                //关闭获取的Redis连接
                jedis.close();
            } else {
                //获取的jedis为null时的处理,一般是抛出异常通过异常抓取进行输出
                new RuntimeException("获取Redis连接错误！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return skuInfo;
    }

    @Override
    public List<SkuInfo> querySkuInfoAndSkuSaleSttrValueBySpuId(Long spuId) {
        return skuInfoMapper.selectSkuInfoAndSkuSaleSttrValueBySpuId(spuId);
    }
}
