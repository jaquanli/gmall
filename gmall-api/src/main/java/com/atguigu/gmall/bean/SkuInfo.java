package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SkuInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long spuId;

    private BigDecimal price;

    private String skuName;

    private String skuDesc;

    private Double weight;

    private Long tmId;

    private Long catalog3Id;

    private String skuDefaultImg;

    private List<SkuImage> skuImageList;

    private List<SkuAttrValue> skuAttrValueList;

    private List<SkuSaleAttrValue> skuSaleAttrValueList;

    @Override
    public String toString() {
        return "SkuInfo{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", price=" + price +
                ", skuName='" + skuName + '\'' +
                ", skuDesc='" + skuDesc + '\'' +
                ", weight=" + weight +
                ", tmId=" + tmId +
                ", catalog3Id=" + catalog3Id +
                ", skuDefaultImg='" + skuDefaultImg + '\'' +
                ", skuImageList=" + skuImageList +
                ", skuAttrValueList=" + skuAttrValueList +
                ", skuSaleAttrValueList=" + skuSaleAttrValueList +
                '}';
    }

    public SkuInfo(Long spuId, BigDecimal price, String skuName, String skuDesc, Double weight, Long tmId, Long catalog3Id, String skuDefaultImg, List<SkuImage> skuImageList, List<SkuAttrValue> skuAttrValueList, List<SkuSaleAttrValue> skuSaleAttrValueList) {
        this.spuId = spuId;
        this.price = price;
        this.skuName = skuName;
        this.skuDesc = skuDesc;
        this.weight = weight;
        this.tmId = tmId;
        this.catalog3Id = catalog3Id;
        this.skuDefaultImg = skuDefaultImg;
        this.skuImageList = skuImageList;
        this.skuAttrValueList = skuAttrValueList;
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }

    public List<SkuImage> getSkuImageList() {
        return skuImageList;
    }

    public void setSkuImageList(List<SkuImage> skuImageList) {
        this.skuImageList = skuImageList;
    }

    public List<SkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<SkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    public List<SkuSaleAttrValue> getSkuSaleAttrValueList() {
        return skuSaleAttrValueList;
    }

    public void setSkuSaleAttrValueList(List<SkuSaleAttrValue> skuSaleAttrValueList) {
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SkuInfo() {
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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc == null ? null : skuDesc.trim();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getTmId() {
        return tmId;
    }

    public void setTmId(Long tmId) {
        this.tmId = tmId;
    }

    public Long getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Long catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg == null ? null : skuDefaultImg.trim();
    }
}