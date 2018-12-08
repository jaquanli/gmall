package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 封装elasticSearch搜索结果的JavaBean
 */
public class SkuLsResult implements Serializable {

    private List<SkuLsInfo> skuLsInfoList;

    private int Total;

    private List<String> valueIdList;

    public SkuLsResult() {
    }

    public SkuLsResult(List<SkuLsInfo> skuLsInfoList, int total, List<String> valueIdList) {
        this.skuLsInfoList = skuLsInfoList;
        Total = total;
        this.valueIdList = valueIdList;
    }

    @Override
    public String toString() {
        return "SkuLsResult{" +
                "skuLsInfoList=" + skuLsInfoList +
                ", Total=" + Total +
                ", valueIdList=" + valueIdList +
                '}';
    }

    public List<SkuLsInfo> getSkuLsInfoList() {
        return skuLsInfoList;
    }

    public void setSkuLsInfoList(List<SkuLsInfo> skuLsInfoList) {
        this.skuLsInfoList = skuLsInfoList;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<String> getValueIdList() {
        return valueIdList;
    }

    public void setValueIdList(List<String> valueIdList) {
        this.valueIdList = valueIdList;
    }
}
