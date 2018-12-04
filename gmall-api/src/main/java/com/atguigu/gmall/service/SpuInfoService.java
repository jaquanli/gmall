package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseSaleAttr;
import com.atguigu.gmall.bean.ResultEntity;
import com.atguigu.gmall.bean.SpuInfo;

import java.util.List;

public interface SpuInfoService {
    List<SpuInfo> querySpuListByCatalog3Id(String catalog3Id);

    List<BaseSaleAttr> queryBaseSaleAttrList();

    ResultEntity<String> saveSpuInfo(SpuInfo spuInfo);
}
