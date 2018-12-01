package com.atguigu.gmall.bean;

import java.math.BigDecimal;

public class OrderDetail {
    private Long id;

    private Long orderId;

    private Long skuId;

    private String skuName;

    private String imgFileName;

    private BigDecimal orderPrice;

    private String skuNums;

    private Long logisticsId;

    private String imgUrl;

    private String skuNum;

    public OrderDetail(Long id, Long orderId, Long skuId, String skuName, String imgFileName, BigDecimal orderPrice, String skuNums, Long logisticsId, String imgUrl, String skuNum) {
        this.id = id;
        this.orderId = orderId;
        this.skuId = skuId;
        this.skuName = skuName;
        this.imgFileName = imgFileName;
        this.orderPrice = orderPrice;
        this.skuNums = skuNums;
        this.logisticsId = logisticsId;
        this.imgUrl = imgUrl;
        this.skuNum = skuNum;
    }

    public OrderDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName == null ? null : imgFileName.trim();
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getSkuNums() {
        return skuNums;
    }

    public void setSkuNums(String skuNums) {
        this.skuNums = skuNums == null ? null : skuNums.trim();
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum == null ? null : skuNum.trim();
    }
}