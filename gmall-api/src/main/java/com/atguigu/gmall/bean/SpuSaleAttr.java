package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

public class SpuSaleAttr implements Serializable {

    @Id
    private Long id;

    private Long spuId;

    private Long saleAttrId;

    private String saleAttrName;

    private List<SpuSaleAttrValue> spuSaleAttrValueList;

    @Override
    public String toString() {
        return "SpuSaleAttr{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", saleAttrId=" + saleAttrId +
                ", saleAttrName='" + saleAttrName + '\'' +
                ", spuSaleAttrValueList=" + spuSaleAttrValueList +
                '}';
    }

    public SpuSaleAttr(Long id, Long spuId, Long saleAttrId, String saleAttrName, List<SpuSaleAttrValue> spuSaleAttrValueList) {
        this.id = id;
        this.spuId = spuId;
        this.saleAttrId = saleAttrId;
        this.saleAttrName = saleAttrName;
        this.spuSaleAttrValueList = spuSaleAttrValueList;
    }

    public SpuSaleAttr() {
        super();
    }

    public List<SpuSaleAttrValue> getSpuSaleAttrValueList() {
        return spuSaleAttrValueList;
    }

    public void setSpuSaleAttrValueList(List<SpuSaleAttrValue> spuSaleAttrValueList) {
        this.spuSaleAttrValueList = spuSaleAttrValueList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(Long saleAttrId) {
        this.saleAttrId = saleAttrId;
    }

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName == null ? null : saleAttrName.trim();
    }
}