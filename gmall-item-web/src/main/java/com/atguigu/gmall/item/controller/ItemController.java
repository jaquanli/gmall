package com.atguigu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.bean.SpuSaleAttr;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuInfoService;
import com.atguigu.gmall.service.SpuSaleAttrService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    private SkuService skuService;

    @Reference
    private SpuInfoService spuInfoService;

    @Reference
    private SpuSaleAttrService spuSaleAttrService;


    @RequestMapping("{skuId}.html")
    public String goItemPage(@PathVariable String skuId, ModelMap modelMap) {
        //查询skuInfo信息
        SkuInfo skuInfo = skuService.querySkuInfoById(skuId);
        if (skuInfo != null){

            Long spuId = skuInfo.getSpuId();
            //查询带有是否配置销售属性的信息的数据
            List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrService.querySpuSaleAttrListCheckBySku(spuId,Long.parseLong(skuId));
            //为获得sku对应的所有销售属性，以便页面跳转时进行页面搜索优化系统速度，直接将查询出的所有结果隐藏到页面中
            List<SkuInfo> skuInfoList = skuService.querySkuInfoAndSkuSaleSttrValueBySpuId(spuId);
            Map<String,String> stringMap = new HashMap<>();

            for (SkuInfo info : skuInfoList) {
                StringBuilder skuSaleAttrValueKey = new StringBuilder();
                List<SkuSaleAttrValue> skuSaleAttrValueList = info.getSkuSaleAttrValueList();
                for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                    skuSaleAttrValueKey.append("|").append(skuSaleAttrValue.getSaleAttrValueId());
                }
                Long skuIdValue = info.getId();
                stringMap.put(skuSaleAttrValueKey.toString(),String.valueOf(skuIdValue));
            }

            String jsonString = JSON.toJSONString(stringMap);


            modelMap.put("skuInfo", skuInfo);
            modelMap.put("spuSaleAttrListCheckBySku", spuSaleAttrList);
            modelMap.put("skuKeyValueJson",jsonString);
            return "item";
        }else{
            return "skuError";
        }


    }
}
