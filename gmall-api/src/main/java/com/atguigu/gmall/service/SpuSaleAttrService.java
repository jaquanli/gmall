package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.SpuSaleAttr;

import java.util.List;

public interface SpuSaleAttrService {
    List<SpuSaleAttr> queryspuSaleAttrListBySpuId(String spuId);
}
