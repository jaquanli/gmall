package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.SkuInfo;

import java.util.List;

public interface SkuService {
    List<SkuInfo> querySkuInfoListBySpuInfoId(String spuInfoId);

    void saveSku(SkuInfo skuInfo);

    SkuInfo querySkuInfoById(String skuId);

    List<SkuInfo> querySkuInfoAndSkuSaleSttrValueBySpuId(Long spuId);

    List<SkuInfo> queryskuInfoAndSkuAttrValueByCatalog3Id(Long catalog3Id);
}
