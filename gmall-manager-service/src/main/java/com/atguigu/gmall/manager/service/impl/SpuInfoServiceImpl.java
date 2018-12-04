package com.atguigu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manager.mapper.*;
import com.atguigu.gmall.service.SpuInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpuInfoServiceImpl implements SpuInfoService {

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Override
    public List<SpuInfo> querySpuListByCatalog3Id(String catalog3Id) {

        List<SpuInfo> spuInfoList = new ArrayList<>();
        if (StringUtils.isBlank(catalog3Id)){
            new RuntimeException("catalog3Id传入的值为空！");
        }else {
            SpuInfo spuInfo = new SpuInfo();
            spuInfo.setCatalog3Id(Long.parseLong(catalog3Id));
            spuInfoList = spuInfoMapper.select(spuInfo);
        }
        return spuInfoList;
    }

    @Override
    public List<BaseSaleAttr> queryBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    public ResultEntity<String> saveSpuInfo(SpuInfo spuInfo) {

        ResultEntity<String> resultEntity = new ResultEntity<>();

        //先判断id是否存在，不存在就添加，存在就更新
        if (spuInfo.getId()== null) {
            spuInfoMapper.insertSelective(spuInfo);

        }else {
            //更新
        }
        //返回插入时生成的id
        Long spuInfoId = spuInfo.getId();
        //获取spuSaleAttr列表信息
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        //获取spuImage列表信息
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        //遍历将spuInfoId封装到spuSaleAttrList中
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            spuSaleAttr.setSpuId(spuInfoId);
            //获取spuSaleAttrValueList
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            //遍历将spuInfoId封装到spuSaleAttrValueList中
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValue.setSpuId(spuInfoId);
            }
            spuSaleAttrValueMapper.insertSpuSaleAttrValueList(spuSaleAttrValueList);
        }
        spuSaleAttrMapper.insertSpuSaleAttrList(spuSaleAttrList);

        for (SpuImage spuImage : spuImageList) {
            spuImage.setSpuId(spuInfoId);
            spuImageMapper.insertSelective(spuImage);
        }
        return resultEntity;
    }
}
