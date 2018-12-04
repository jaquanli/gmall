package com.atguigu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.SkuAttrValue;
import com.atguigu.gmall.bean.SkuImage;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.manager.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.manager.mapper.SkuImageMapper;
import com.atguigu.gmall.manager.mapper.SkuInfoMapper;
import com.atguigu.gmall.manager.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public List<SkuInfo> querySkuInfoListBySpuInfoId(String spuInfoId) {

        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(Long.parseLong(spuInfoId));

        return skuInfoMapper.select(skuInfo);
    }

    @Override
    public void saveSku(SkuInfo skuInfo) {

        if (skuInfo != null){
            skuInfoMapper.insertSelective(skuInfo);
            Long skuId = skuInfo.getId();

            List<SkuImage> skuImageList = skuInfo.getSkuImageList();
            if (skuImageList != null){
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
}
