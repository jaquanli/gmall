package com.atguigu.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.bean.BaseCatalog2;
import com.atguigu.gmall.bean.BaseCatalog3;
import com.atguigu.gmall.service.BaseAttrInfoService;
import com.atguigu.gmall.service.BaseCatalog1Service;
import com.atguigu.gmall.service.BaseCatalog2Service;
import com.atguigu.gmall.service.BaseCatalog3Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AttrManagerController {

    @Reference
    BaseCatalog1Service baseCatalog1Service;

    @Reference
    BaseCatalog2Service baseCatalog2Service;

    @Reference
    BaseCatalog3Service baseCatalog3Service;

    @Reference
    BaseAttrInfoService baseAttrInfoService;

    @RequestMapping("attrListPage")
    public String goAttrListPage(){
        return "attrListPage";
    }

    @ResponseBody
    @RequestMapping("get/catalog1/list")
    public List<BaseCatalog1> getCatalog1List(){

        List<BaseCatalog1> baseCatalog1List = baseCatalog1Service.queryCatalog1List();

        return  baseCatalog1List;
    }

    @ResponseBody
    @RequestMapping("get/catalog2/list")
    public List<BaseCatalog2> getCatalog2List(@RequestParam("catalog1Id") String catalog1Id){

        List<BaseCatalog2> baseCatalog2List =  baseCatalog2Service.queryCatalog2ListByCatalog1Id(catalog1Id);

        return  baseCatalog2List;
    }

    @ResponseBody
    @RequestMapping("get/catalog3/list")
    public List<BaseCatalog3> getCatalog3List(@RequestParam("catalog2Id") String catalog2Id){

        List<BaseCatalog3> baseCatalog3List = baseCatalog3Service.queryCatalog3ListByCatalog2Id(catalog2Id);

        return baseCatalog3List;

    }

    @ResponseBody
    @RequestMapping("get/attr/info/list")
    public List<BaseAttrInfo> getAttrInfoList(@RequestParam("catalog3Id") String catalog3Id) {

        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoService.queryAttrInfoListByCatalog3Id(catalog3Id);

        return  baseAttrInfoList;

    }

}
