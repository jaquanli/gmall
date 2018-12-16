package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 *匹配elasticsearch搜索时用的JavaBean
 */
public class SkuLsParam implements Serializable{

    private Long  catalog3Id;

    private String[] valueId;

    private String keyword;

    private Integer  pageNo=1;

    private Integer pageSize=20;

    public SkuLsParam() {
    }

    public SkuLsParam(Long catalog3Id, String[] valueId, String keyword, Integer pageNo, Integer pageSize) {
        this.catalog3Id = catalog3Id;
        this.valueId = valueId;
        this.keyword = keyword;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Long getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Long catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String[] getValueId() {
        return valueId;
    }

    public void setValueId(String[] valueId) {
        this.valueId = valueId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "SkuLsParam{" +
                "catalog3Id=" + catalog3Id +
                ", valueId=" + Arrays.toString(valueId) +
                ", keyword='" + keyword + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
