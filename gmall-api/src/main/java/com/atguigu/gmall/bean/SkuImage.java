package com.atguigu.gmall.bean;

public class SkuImage {
    private Long id;

    private Long skuId;

    private String imgName;

    private String imgUrl;

    private Long spuImgId;

    private String isDefault;

    public SkuImage(Long id, Long skuId, String imgName, String imgUrl, Long spuImgId, String isDefault) {
        this.id = id;
        this.skuId = skuId;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.spuImgId = spuImgId;
        this.isDefault = isDefault;
    }

    public SkuImage() {
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

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Long getSpuImgId() {
        return spuImgId;
    }

    public void setSpuImgId(Long spuImgId) {
        this.spuImgId = spuImgId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }
}