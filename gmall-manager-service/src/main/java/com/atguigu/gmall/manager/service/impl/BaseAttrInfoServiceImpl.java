package com.atguigu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.manager.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.service.BaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BaseAttrInfoServiceImpl implements BaseAttrInfoService {

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Override
    public List<BaseAttrInfo> queryAttrInfoListByCatalog3Id(String catalog3Id) {

        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();

        baseAttrInfo.setCatalog3Id(Long.parseLong(catalog3Id));

        return baseAttrInfoMapper.select(baseAttrInfo);
    }
}
