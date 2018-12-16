package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

public class CartInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long userId;

    private Long skuId;

    private BigDecimal cartPrice;

    private Integer quantity;

    private String imgUrl;

    private String isChecked;

    private BigDecimal skuPrice;

    private Integer skuNum;

    private String skuName;

    @Override
    public String toString() {
        return "CartInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", skuId=" + skuId +
                ", cartPrice=" + cartPrice +
                ", quantity=" + quantity +
                ", imgUrl='" + imgUrl + '\'' +
                ", isChecked='" + isChecked + '\'' +
                ", skuPrice=" + skuPrice +
                ", skuNum=" + skuNum +
                ", skuName='" + skuName + '\'' +
                '}';
    }

    public CartInfo(Long id, Long userId, Long skuId, BigDecimal cartPrice, Integer quantity, String imgUrl, String isChecked, BigDecimal skuPrice, Integer skuNum, String skuName) {
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

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }
}