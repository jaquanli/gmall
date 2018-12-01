package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseCatalog2;

import java.util.List;

public interface BaseCatalog2Service {
    List<BaseCatalog2> queryCatalog2ListByCatalog1Id(String catalog1Id);
}
