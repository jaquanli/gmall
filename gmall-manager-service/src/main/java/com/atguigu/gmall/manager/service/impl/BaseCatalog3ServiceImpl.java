package com.atguigu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.BaseCatalog3;
import com.atguigu.gmall.manager.mapper.BaseCatalog3Mapper;
import com.atguigu.gmall.service.BaseCatalog3Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BaseCatalog3ServiceImpl implements BaseCatalog3Service {

    @Autowired
    BaseCatalog3Mapper baseCatalog3Mapper;

    @Override
    public List<BaseCatalog3> queryCatalog3ListByCatalog2Id(String catalog2Id) {

        BaseCatalog3 baseCatalog3 = new BaseCatalog3();

        baseCatalog3.setCatalog2Id(Long.parseLong(catalog2Id));

        return baseCatalog3Mapper.select(baseCatalog3);
    }
}
