package com.atguigu.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.bean.SpuSaleAttr;
import com.atguigu.gmall.service.BaseAttrInfoService;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuImageService;
import com.atguigu.gmall.service.SpuSaleAttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class SkuManagerController {

    @Reference
    private SkuService skuService;

    @Reference
    private BaseAttrInfoService baseAttrInfoService;

    @Reference
    private SpuSaleAttrService spuSaleAttrService;

    @Reference
    private SpuImageService spuImageService;

    @ResponseBody
    @RequestMapping("skuInfoListBySpu")
    public List<SkuInfo> skuInfoListBySpu(@RequestParam("spuId") String spuInfoId){

        return skuService.querySkuInfoListBySpuInfoId(spuInfoId);
    }

    @ResponseBody
    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(@RequestParam("catalog3Id") String catalog3Id){

        return baseAttrInfoService.queryAttrInfoListByCatalog3Id(catalog3Id);
    }

    @ResponseBody
    @RequestMapping("spuSaleAttrList")
    public  List<SpuSaleAttr> spuSaleAttrList(@RequestParam("spuId") String spuId){

        return spuSaleAttrService.queryspuSaleAttrListBySpuId(spuId);

    }

    @ResponseBody
    @RequestMapping("spuImageList")
    public List<SpuImage> spuImageList(@RequestParam("spuId") String spuId){

        return spuImageService.querySpuImageListBySpuId(spuId);

    }

    @ResponseBody
    @RequestMapping("saveSku")
    public String saveSku(SkuInfo skuInfo){

        skuService.saveSku(skuInfo);

        return "success";

    }
}
