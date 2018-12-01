package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseCatalog3;

import java.util.List;

public interface BaseCatalog3Service {
    List<BaseCatalog3> queryCatalog3ListByCatalog2Id(String catalog2Id);
}
