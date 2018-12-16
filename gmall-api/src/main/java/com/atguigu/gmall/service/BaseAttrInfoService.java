package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseAttrValue;
import com.atguigu.gmall.bean.ResultEntity;

import java.util.List;
import java.util.Set;

public interface BaseAttrInfoService {
    List<BaseAttrInfo> queryAttrInfoListByCatalog3Id(String catalog3Id);

    ResultEntity<String> saveAttrInfoAndAttrValue(BaseAttrInfo baseAttrInfo);

    ResultEntity<String> saveBaseAttrValueByValueListAndInfoId(List<BaseAttrValue> baseAttrValueList, Long infoId);

    List<BaseAttrInfo> queryAttrInfoByValueIds(Set<String> valueIds);
}
