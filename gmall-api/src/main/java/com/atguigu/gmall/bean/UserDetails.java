package com.atguigu.gmall.bean;

import java.util.Date;
/**
 * 暂用不着
 */
public class UserDetails {
    private Long id;

    private Long idCard;

    private Long userId;

    private String userPhone;

    private String hometown;

    private Long addressId;

    private String sex;

    private Date createTime;

    public UserDetails(Long id, Long idCard, Long userId, String userPhone, String hometown, Long addressId, String sex, Date createTime) {
        this.id = id;
        this.idCard = idCard;
        this.userId = userId;
        this.userPhone = userPhone;
        this.hometown = hometown;
        this.addressId = addressId;
        this.sex = sex;
        this.createTime = createTime;
    }

    public UserDetails() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown == null ? null : hometown.trim();
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}