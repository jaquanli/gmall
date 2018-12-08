package com.atguigu.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseSaleAttr;
import com.atguigu.gmall.bean.ResultEntity;
import com.atguigu.gmall.bean.SpuInfo;
import com.atguigu.gmall.manager.util.GmallUploadUtil;
import com.atguigu.gmall.service.SpuInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class SpuManagerController {

    //引用配置文件中的自定义配置
    @Value("${fileServer.url}")
    private String fileUrl;

    @Reference
    private SpuInfoService spuInfoService;

    @RequestMapping("go/spu/list/page")
    public String goSpuListPage(){
        return "spuListPage";
    }

    @ResponseBody
    @RequestMapping("get/spu/list/catalog3Id")
    public List<SpuInfo> getSpuListByCatalog3Id(@RequestParam("catalog3Id") String catalog3Id){

        return  spuInfoService.querySpuListByCatalog3Id(catalog3Id);
    }

    @ResponseBody
    @RequestMapping("baseSaleAttrList")
    public List<BaseSaleAttr> getBaseSaleAttrList(){

        return spuInfoService.queryBaseSaleAttrList();
    }

    @ResponseBody
    @RequestMapping("saveSpuInfo")
    public ResultEntity<String> saveSpuInfo(SpuInfo spuInfo){

        return spuInfoService.saveSpuInfo(spuInfo);

    }

    @ResponseBody
    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){

        if (multipartFile != null) {
            System.err.println(fileUrl);
            return GmallUploadUtil.fileUpload(multipartFile, fileUrl);
        }
        return  null;
    }
}
