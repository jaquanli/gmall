package com.atguigu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseAttrValue;
import com.atguigu.gmall.bean.ResultEntity;
import com.atguigu.gmall.manager.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.manager.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.service.BaseAttrInfoService;
import com.atguigu.gmall.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BaseAttrInfoServiceImpl implements BaseAttrInfoService {

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<BaseAttrInfo> queryAttrInfoListByCatalog3Id(String catalog3Id) {

        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();

        baseAttrInfo.setCatalog3Id(Long.parseLong(catalog3Id));

        List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.select(baseAttrInfo);

        for (BaseAttrInfo attrInfo : baseAttrInfos) {

            Long attrId = attrInfo.getId();
            BaseAttrValue attrValue = new BaseAttrValue();
            attrValue.setAttrId(attrId);
            List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.select(attrValue);
            attrInfo.setAttrValueList(baseAttrValues);
        }
        return baseAttrInfos;
    }

    /**
     * 添加属性并添加属性值，根据传入的值是否为空做出不同的业务处理
     *
     * @param baseAttrInfo 属性信息对象
     * @return 封装好成功和消息的结果
     */
    @Override
    public ResultEntity<String> saveAttrInfoAndAttrValue(BaseAttrInfo baseAttrInfo) {

        ResultEntity<String> resultEntity = new ResultEntity<>();

        //创建封装消息的List对象
        List<String> massageList = new ArrayList<>();

        //获取id是否存在
        Long attrInfoId = baseAttrInfo.getId();

        //获取属性名
        String attrName = baseAttrInfo.getAttrName();
        //获取三级分类id
        Long catalog3Id = baseAttrInfo.getCatalog3Id();

        //获取添加的属性值list
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();

        //不存在就插入
        if (attrInfoId == null) {
            //根据属性名和三级id信息的对象查询对象
            BaseAttrInfo baseAttrInfoByName = baseAttrInfoMapper.selectBaseAttrInfoByAttrNameAndCatalog3Id(attrName, catalog3Id);

            //如果查不到对象，进行插入
            if (baseAttrInfoByName == null) {

                //进行添加attrInfo
                baseAttrInfoMapper.insertSelective(baseAttrInfo);
                resultEntity.setMassage("属性：【" + attrName + "】添加成功");
                resultEntity.setResult(ResultEntity.SUCCESS);

                //获取添加后返回的id
                Long infoId = baseAttrInfo.getId();

                //调用添加属性值方法并返回结果
                ResultEntity<String> saveValueResultEntity = saveBaseAttrValueByValueListAndInfoId(attrValueList, infoId);
                resultEntity.setMassageList(saveValueResultEntity.getMassageList());
                resultEntity.setResult(saveValueResultEntity.getResult());
            } else {
                //获取按属性名查出存在属性Id
                Long infoByNameId = baseAttrInfoByName.getId();
                //调用添加属性值方法并返回结果
                ResultEntity<String> saveValueResultEntity = saveBaseAttrValueByValueListAndInfoId(attrValueList, infoByNameId);
                resultEntity.setMassageList(saveValueResultEntity.getMassageList());
                resultEntity.setResult(saveValueResultEntity.getResult());
            }

        } else {
            //更新
            //根据有属性名信息的对象查询对象
            BaseAttrInfo baseAttrInfoByName = baseAttrInfoMapper.selectBaseAttrInfoByAttrNameAndCatalog3Id(attrName, catalog3Id);
            if (baseAttrInfoByName == null) {
                baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
                resultEntity.setResult(ResultEntity.SUCCESS);
                resultEntity.setMassage("属性:【" + attrName + "】更新成功！");
            } else {
                //调用添加属性值方法并返回结果
                ResultEntity<String> saveValueResultEntity = saveBaseAttrValueByValueListAndInfoId(attrValueList, attrInfoId);
                resultEntity.setMassageList(saveValueResultEntity.getMassageList());
                resultEntity.setResult(saveValueResultEntity.getResult());
            }
        }
        return resultEntity;
    }

    /**
     * 根据传入的属性值List和属性id添加到数据库，并根据名称判断是否为重复插入，
     *
     * @param baseAttrValueList 传入的属性值List
     * @param infoId            属性id
     * @return 封装好成功和消息的结果
     */
    @Override
    public ResultEntity<String> saveBaseAttrValueByValueListAndInfoId(List<BaseAttrValue> baseAttrValueList, Long infoId) {

        ResultEntity<String> resultEntity = new ResultEntity<>();
        List<String> massageList = new ArrayList<>();
        //遍历出参数值
        for (BaseAttrValue attrValue : baseAttrValueList) {
            //获取每个value对象的id属性值
            Long attrValueId = attrValue.getId();
            //获取每个value对象的name属性值
            String valueName = attrValue.getValueName();

            //判断传入的id是否为空，不为空就按照id进行更新name，为空就添加属性
            if (attrValueId == null) {
                //根据name和属性id查询属性值对象
                BaseAttrValue attrValueByValueName = baseAttrValueMapper.selectBaseAttrValueByValueNameAndAttrInfoId(valueName, infoId);
                //如果查不到，就进行插入处理
                if (attrValueByValueName == null) {
                    //把添加返回的值封装到每一个属性值对象
                    attrValue.setAttrId(infoId);
                    //进行往数据库添加属性值
                    baseAttrValueMapper.insertSelective(attrValue);
                    //操作成功
                    resultEntity.setResult(ResultEntity.SUCCESS);
                    massageList.add("属性值：【" + valueName + "】添加成功");
                    resultEntity.setMassageList(massageList);
                } else {

                    //操作失败
                    resultEntity.setResult(ResultEntity.FAILED);
                    //将对应数据库存在的值封装到list
                    massageList.add("属性值：【" + valueName + "】已存在请勿重复添加");
                    //将数据库存在的值的信息通过消息返回
                    resultEntity.setMassageList(massageList);
                }
            } else {


                //根据name和属性id查询属性值对象
                BaseAttrValue attrValueByValueName = baseAttrValueMapper.selectBaseAttrValueByValueNameAndAttrInfoId(valueName, infoId);
                Long id = attrValueByValueName.getId();

                //在这里如果为空就说明值发生了改变，进行更新,不为空说明值没有发生改变，就不更新
                if (attrValueByValueName == null) {
                    //进行更新
                    baseAttrValueMapper.updateByPrimaryKeySelective(attrValue);
                    resultEntity.setResult(ResultEntity.SUCCESS);
                    massageList.add("编号：【" + attrValueId + "】更新成功");
                    resultEntity.setMassageList(massageList);
                }
            }
        }
        return resultEntity;
    }

    @Override
    public List<BaseAttrInfo> queryAttrInfoByValueIds(Set<String> valueIds) {

//        //建立集合封装去redis获取的数据
//        List<BaseAttrInfo> baseAttrInfos = new ArrayList<>();
//        //建立集合封装每一个baseAttrInfo对应的key
//        List<String> baseAttrInfoKeys = new ArrayList<>();
//        //建立集合封装每一个baseAttrInfo对应的锁
//        List<String> baseAttrInfoLocks = new ArrayList<>();
//        //遍历valueIds组装key和Lock
//        for (String valueId : valueIds) {
//            String baseAttrInfoKey = "sku:" + valueId + ":baseAttrInfo";
//            baseAttrInfoKeys.add(baseAttrInfoKey);
//            String skuInfoLock = "sku:" + valueId + ":baseAttrInfo:Lock";
//            baseAttrInfoLocks.add(skuInfoLock);
//        }
//
//        try {
//            //获取jedis连接
//            Jedis jedis = redisUtil.getJedis();
//
//            if (jedis != null) {
//                //遍历塞入的key
//                for (String baseAttrInfoKey : baseAttrInfoKeys) {
//                    //先从redis获取数据
//                    String baseAttrInfoValue = jedis.get(baseAttrInfoKey);
//                    //如果没有数据
//                    if (StringUtils.isBlank(baseAttrInfoValue)) {
//                        //这里是在redis没有获取到数据
//                        //拿到操作每一个数据的分布式锁
//                        for (String baseAttrInfoLock : baseAttrInfoLocks) {
//                            //设置分布式锁
//                            Long setnxResult = jedis.setnx(baseAttrInfoLock, baseAttrInfoLock);
//                            //设置锁的过期时间，以防线程死亡，阻碍下一个线程使用
//                            jedis.expire(baseAttrInfoLock, 20);
//                            //获取成功
//                            if (setnxResult == 1) {
//                                //这里是获取分布式锁成功
//                                //DB获取数据
//                                baseAttrInfos = baseAttrInfoMapper.selectBaseAttrInfoByValueIds(valueIds);
//                                //如果DB获取数据成功
//                                if (baseAttrInfos != null) {
//                                    //遍历得到的每一个数据
//                                    for (BaseAttrInfo baseAttrInfo : baseAttrInfos) {
//                                        //转存JSON对象
//                                        baseAttrInfoValue = JSON.toJSONString(baseAttrInfo);
//                                        //同步redis
//                                        jedis.set(baseAttrInfoKey, baseAttrInfoValue);
//                                        //使用完后删除锁
//                                        jedis.del(baseAttrInfoLock);
//                                    }
//
//                                } else {
//                                    //这里是未从数据库获得数据
//                                    /*
//                                        这里也可以采用BloomFilter布隆过滤器来解决，以后再研究
//                                    */
//
//                                    //未获取到数据库数据时，设置一个值为空值，并过期时间为3分钟
//                                    int timeOut = 60 * 1000 * 3;
//                                    jedis.set(baseAttrInfoKey, "empty", "nx", "px", timeOut);
//                                    //删除锁操作，方便下一个线程进行使用
//                                    jedis.del(baseAttrInfoLock);
//                                }
//                            } else {
//                                //这里是获取分布式锁失败
//                                Thread.sleep(5000);
//                                //开始自旋
//                                return queryAttrInfoByValueIds(valueIds);
//                            }
//                        }
//                    } else {
//                        if (!("empty".equals(baseAttrInfoValue))){
//                            //这里是在redis获取到了数据
//                            BaseAttrInfo baseAttrInfo = JSON.parseObject(baseAttrInfoValue, BaseAttrInfo.class);
//                            if (baseAttrInfo != null) {
//                                baseAttrInfos.add(baseAttrInfo);
//                            }
//
//                        }
//                    }
//                }
//                //关闭
//                jedis.close();
//            } else {
//                new RuntimeException("获取redis连接错误！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return baseAttrInfoMapper.selectBaseAttrInfoByValueIds(valueIds);
    }
}
