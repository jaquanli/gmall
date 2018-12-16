package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 匹配数据库中的与之对应的SkuInfo，用于封装数据库查询结果，封装到elasticSearch中
 */
public class SkuLsInfo implements Serializable {


    private Long id;

    private BigDecimal price;

    private String skuName;

    private String skuDesc;

    private Long catalog3Id;

    private String skuDefaultImg;

    private Long hotScore = 0L;

    private List<SkuLsAttrValue> skuAttrValueList;

    public SkuLsInfo() {
    }

    public SkuLsInfo(Long id, BigDecimal price, String skuName, String skuDesc, Long catalog3Id, String skuDefaultImg, Long hotScore, List<SkuLsAttrValue> skuAttrValueList) {
        this.id = id;
        this.price = price;
        this.skuName = skuName;
        this.skuDesc = skuDesc;
        this.catalog3Id = catalog3Id;
        this.skuDefaultImg = skuDefaultImg;
        this.hotScore = hotScore;
        this.skuAttrValueList = skuAttrValueList;
    }

    @Override
    public String toString() {
        return "SkuLsInfo{" +
                "id=" + id +
                ", price=" + price +
                ", skuName='" + skuName + '\'' +
                ", skuDesc='" + skuDesc + '\'' +
                ", catalog3Id=" + catalog3Id +
                ", skuDefaultImg='" + skuDefaultImg + '\'' +
                ", hotScore=" + hotScore +
                ", skuAttrValueList=" + skuAttrValueList +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Long catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public Long getHotScore() {
        return hotScore;
    }

    public void setHotScore(Long hotScore) {
        this.hotScore = hotScore;
    }

    public List<SkuLsAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<SkuLsAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }
}
