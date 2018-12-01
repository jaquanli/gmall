package com.atguigu.gmall.bean;

public class SpuSaleAttr {
    private Long id;

    private Long spuId;

    private Long saleAttrId;

    private String saleAttrName;

    public SpuSaleAttr(Long id, Long spuId, Long saleAttrId, String saleAttrName) {
        this.id = id;
        this.spuId = spuId;
        this.saleAttrId = saleAttrId;
        this.saleAttrName = saleAttrName;
    }

    public SpuSaleAttr() {
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

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName == null ? null : saleAttrName.trim();
    }
}