package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseAttrInfo;

import java.util.List;

public interface BaseAttrInfoService {
    List<BaseAttrInfo> queryAttrInfoListByCatalog3Id(String catalog3Id);
}
