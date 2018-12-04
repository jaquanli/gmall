package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class SkuSaleAttrValue  implements Serializable {

    @Id
    private Long id;

    private Long skuId;

    private Long saleAttrId;

    private Long saleAttrValueId;

    private String saleAttrName;

    private String saleAttrValueName;

    @Override
    public String toString() {
        return "SkuSaleAttrValue{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", saleAttrId=" + saleAttrId +
                ", saleAttrValueId=" + saleAttrValueId +
                ", saleAttrName='" + saleAttrName + '\'' +
                ", saleAttrValueName='" + saleAttrValueName + '\'' +
                '}';
    }

    public SkuSaleAttrValue(Long id, Long skuId, Long saleAttrId, Long saleAttrValueId, String saleAttrName, String saleAttrValueName) {
        this.id = id;
        this.skuId = skuId;
        this.saleAttrId = saleAttrId;
        this.saleAttrValueId = saleAttrValueId;
        this.saleAttrName = saleAttrName;
        this.saleAttrValueName = saleAttrValueName;
    }

    public SkuSaleAttrValue() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(Long saleAttrId) {
        this.saleAttrId = saleAttrId;
    }

    public Long getSaleAttrValueId() {
        return saleAttrValueId;
    }

    public void setSaleAttrValueId(Long saleAttrValueId) {
        this.saleAttrValueId = saleAttrValueId;
    }

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName == null ? null : saleAttrName.trim();
    }

    public String getSaleAttrValueName() {
        return saleAttrValueName;
    }

    public void setSaleAttrValueName(String saleAttrValueName) {
        this.saleAttrValueName = saleAttrValueName == null ? null : saleAttrValueName.trim();
    }
}