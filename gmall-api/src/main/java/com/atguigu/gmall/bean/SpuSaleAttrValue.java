package com.atguigu.gmall.bean;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

public class SpuSaleAttrValue implements Serializable {

    @Id
    private Long id;

    private Long spuId;

    private Long saleAttrId;

    private String saleAttrValueName;

    @Transient
    private String isCheck;

    public SpuSaleAttrValue(Long id, Long spuId, Long saleAttrId, String saleAttrValueName, String isCheck) {
        this.id = id;
        this.spuId = spuId;
        this.saleAttrId = saleAttrId;
        this.saleAttrValueName = saleAttrValueName;
        this.isCheck = isCheck;
    }

    public SpuSaleAttrValue() {
        super();
    }

    @Override
    public String toString() {
        return "SpuSaleAttrValue{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", saleAttrId=" + saleAttrId +
                ", saleAttrValueName='" + saleAttrValueName + '\'' +
                ", isCheck='" + isCheck + '\'' +
                '}';
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
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

    public String getSaleAttrValueName() {
        return saleAttrValueName;
    }

    public void setSaleAttrValueName(String saleAttrValueName) {
        this.saleAttrValueName = saleAttrValueName == null ? null : saleAttrValueName.trim();
    }
}