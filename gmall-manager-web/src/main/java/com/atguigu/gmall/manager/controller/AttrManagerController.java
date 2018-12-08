package com.atguigu.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class AttrManagerController {

    @Reference
    private BaseCatalog1Service baseCatalog1Service;

    @Reference
    private BaseCatalog2Service baseCatalog2Service;

    @Reference
    private BaseCatalog3Service baseCatalog3Service;

    @Reference
    private BaseAttrInfoService baseAttrInfoService;

    @Reference
    private BaseAttrValueService baseAttrValueService;

    @RequestMapping("go/attr/list/page")
    public String goAttrListPage(){
        return "attrListPage";
    }

    @ResponseBody
    @RequestMapping("get/catalog1/list/all")
    public List<BaseCatalog1> getCatalog1ListAll(){


        return baseCatalog1Service.queryCatalog1List();
    }

    @ResponseBody
    @RequestMapping("get/catalog2/list/catalog1Id")
    public List<BaseCatalog2> getCatalog2ListByCatalog1Id(@RequestParam("catalog1Id") String catalog1Id){

        return baseCatalog2Service.queryCatalog2ListByCatalog1Id(catalog1Id);
    }

    @ResponseBody
    @RequestMapping("get/catalog3/list/catalog2Id")
    public List<BaseCatalog3> getCatalog3ListByCatalog2Id(@RequestParam("catalog2Id") String catalog2Id){

        return baseCatalog3Service.queryCatalog3ListByCatalog2Id(catalog2Id);

    }

    @ResponseBody
    @RequestMapping("get/attr/info/list/catalog3Id")
    public List<BaseAttrInfo> getAttrInfoListByCatalog3Id(@RequestParam("catalog3Id") String catalog3Id) {

        return baseAttrInfoService.queryAttrInfoListByCatalog3Id(catalog3Id);

    }

    @ResponseBody
    @RequestMapping("add/attr/info/and/value")
    public ResultEntity<String> addAttrInfoAndAttrValue(BaseAttrInfo baseAttrInfo){

        return baseAttrInfoService.saveAttrInfoAndAttrValue(baseAttrInfo);
    }

    @ResponseBody
    @RequestMapping("get/attr/value/list/attrInfoId")
    public List<BaseAttrValue> getBaseAttrValueListByAttrInfoId(@RequestParam("attrInfoId") String attrInfoId){

        return baseAttrValueService.queryBaseAttrValueListByAttrInfoId(attrInfoId);
    }

}
