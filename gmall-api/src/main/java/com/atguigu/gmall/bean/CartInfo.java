package com.atguigu.gmall.bean;

import java.math.BigDecimal;

public class CartInfo {
    private Long id;

    private Long userId;

    private Long skuId;

    private BigDecimal cartPrice;

    private Integer quantity;

    private String imgUrl;

    private String isChecked;

    private BigDecimal skuPrice;

    private BigDecimal skuNum;

    private String skuName;

    public CartInfo(Long id, Long userId, Long skuId, BigDecimal cartPrice, Integer quantity, String imgUrl, String isChecked, BigDecimal skuPrice, BigDecimal skuNum, String skuName) {
        this.id = id;
        this.userId = userId;
        this.skuId = skuId;
        this.cartPrice = cartPrice;
        this.quantity = quantity;
        this.imgUrl = imgUrl;
        this.isChecked = isChecked;
        this.skuPrice = skuPrice;
        this.skuNum = skuNum;
        this.skuName = skuName;
    }

    public CartInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(BigDecimal cartPrice) {
        this.cartPrice = cartPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked == null ? null : isChecked.trim();
    }

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    public BigDecimal getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(BigDecimal skuNum) {
        this.skuNum = skuNum;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }
}