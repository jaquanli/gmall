package com.atguigu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.BaseAttrValue;
import com.atguigu.gmall.manager.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.service.BaseAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BaseAttrValueServiceImpl implements BaseAttrValueService {

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseAttrValue> queryBaseAttrValueListByAttrInfoId(String attrInfoId) {

        BaseAttrValue baseAttrValue = new BaseAttrValue();

        baseAttrValue.setAttrId(Long.parseLong(attrInfoId));

        return baseAttrValueMapper.select(baseAttrValue);
    }

    @Override
    public BaseAttrValue queryBaseAttrValueListByValueName(String valueName) {
        BaseAttrValue attrValue = new BaseAttrValue();
        attrValue.setValueName(valueName);
        return baseAttrValueMapper.selectOne(attrValue);
    }
}
