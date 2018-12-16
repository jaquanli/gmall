package com.atguigu.gmall.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.BaseAttrInfoService;
import com.atguigu.gmall.service.ListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ListController {

    @Reference
    private ListService listService;

    @Reference
    private BaseAttrInfoService baseAttrInfoService;

    @RequestMapping("list.html")
    public String goListPage(SkuLsParam skuLsParam, ModelMap modelMap){

        List<BaseAttrInfo> baseAttrInfos;
        //创建一个set集合，具有去重效果
        Set<String> valueIds = new HashSet<>();
        //查skuLSInfo
        List<SkuLsInfo> skuLsInfos = listService.searchSkuLsInfo(skuLsParam);
            //
        if (skuLsInfos.size() != 0) {
            //将从数据库得到的数据提交给页面
            modelMap.put("skuLsInfoList",skuLsInfos);
            //遍历
            for (SkuLsInfo skuLsInfo : skuLsInfos) {
                List<SkuLsAttrValue> skuAttrValueList = skuLsInfo.getSkuAttrValueList();
                for (SkuLsAttrValue skuLsAttrValue : skuAttrValueList) {
                    Long valueId = skuLsAttrValue.getValueId();
                    //将遍历出的id塞入set集合
                    valueIds.add(String.valueOf(valueId));
                }
            }
            //根据valueId集合查询属性
            baseAttrInfos = baseAttrInfoService.queryAttrInfoByValueIds(valueIds);

            //属性集合不为空时
            if (baseAttrInfos != null) {
                //获得页面参数valueId，判定为要删除的id
                String[] delValueIds = skuLsParam.getValueId();
                //如果不为空,进行删除操作
                if (delValueIds != null && delValueIds.length > 0) {
                    //在这里新建集合
                    List<Crumb> crumbs = new ArrayList<>();
                    //遍历页面传过来的多个valueId
                    for (String delValueId : delValueIds) {
                        //创建属性集合的迭代，在这里每次返回页面之前进行迭代
                        Iterator<BaseAttrInfo> baseAttrInfoIts = baseAttrInfos.iterator();
                        //在这里创建crumb
                        Crumb crumb = new Crumb();
                    //开始迭代
                    while (baseAttrInfoIts.hasNext()){
                        //通过迭代获得baseAttrInfo
                        BaseAttrInfo baseAttrInfo = baseAttrInfoIts.next();
                        //获得AttrValueList
                        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
                        //遍历
                        for (BaseAttrValue attrValue : attrValueList) {
                            //获得valueId
                            Long valueId = attrValue.getId();
                                //当要删除的id与数据库中的valueId相等时
                                if (Long.parseLong(delValueId) == valueId){
                                    //获取URL地址
                                    String crumbUrl = getListUrlParam(skuLsParam, delValueId);
                                    //设置URL地址
                                    crumb.setUrlParam(crumbUrl);
                                    //设置名称
                                    crumb.setValueName(attrValue.getValueName());
                                    //删除当前的baseAttrInfo
                                    baseAttrInfoIts.remove();
                                }
                            }
                        }
                        //加入list
                        crumbs.add(crumb);
                    }
                    modelMap.put("attrValueSelectedList",crumbs);
                }
                //将从数据库得到并处理的数据提交给页面
                modelMap.put("attrList",baseAttrInfos);
            }




            //获取关键字
            String keyword = skuLsParam.getKeyword();
            //反馈给页面
            modelMap.put("keyword",keyword);
            //获得拼接出的URL地址
            String url = getListUrlParam(skuLsParam);

            modelMap.put("urlParam",url);
        }else {
            return "error";
        }

        return "list";
    }

    /**
     * 拼出URL字符串
     * @param skuLsParam
     * @return
     */
    private String getListUrlParam(SkuLsParam skuLsParam,String ...delValueIds) {

        StringBuilder url = new StringBuilder();

        Long catalog3Id = skuLsParam.getCatalog3Id();
        String keyword = skuLsParam.getKeyword();
        String[] valueIds = skuLsParam.getValueId();
        //当有三级分类id时，说明是从列表筛选入口进来的
        if (catalog3Id != null){
            url.append("catalog3Id=").append(catalog3Id);
        }
        //当有keyword时，说明是从搜索入口进来的
        if (StringUtils.isNotBlank(keyword)){
            url.append("keyword=").append(keyword);
        }
        //判断可变形参是否存在值
        if (delValueIds.length == 0) {
            //不存在值时说明
            if (valueIds != null && valueIds.length > 0){
                for (String valueId : valueIds) {
                    url.append("&valueId=").append(valueId);
                }
            }
        }else {

            if (valueIds != null && valueIds.length > 0){
                for (String valueId : valueIds) {
                    for (String delValueId : delValueIds) {
                        if (!delValueId.equals(valueId)){
                            url.append("&valueId=").append(valueId);
                        }
                    }
                }
            }
        }



        return url.toString();
    }

}
