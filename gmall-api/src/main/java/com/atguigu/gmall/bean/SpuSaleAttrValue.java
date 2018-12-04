package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class SpuSaleAttrValue implements Serializable {

    @Id
    private Long id;

    private Long spuId;

    private Long saleAttrId;

    private String saleAttrValueName;


    public SpuSaleAttrValue(Long id, Long spuId, Long saleAttrId, String saleAttrValueName) {
        this.id = id;
        this.spuId = spuId;
        this.saleAttrId = saleAttrId;
        this.saleAttrValueName = saleAttrValueName;
    }

    public SpuSaleAttrValue() {
        super();
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