package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseAttrValue;

import java.util.List;

public interface BaseAttrValueService {
    List<BaseAttrValue> queryBaseAttrValueListByAttrInfoId(String attrInfoId);

    BaseAttrValue queryBaseAttrValueListByValueName(String valueName);
}
