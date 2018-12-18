package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentInfo implements Serializable {

    @Id
    private Long id;

    private String outTradeNo;

    private String orderId;

    private String alipayTradeNo;

    private BigDecimal totalAmount;

    private String subject;

    private String paymentStatus;

    private Date createTime;

    private Date confirmTime;

    private String callbackContent;

    private Date callbackTime;

    public PaymentInfo(Long id, String outTradeNo, String orderId, String alipayTradeNo, BigDecimal totalAmount, String subject, String paymentStatus, Date createTime, Date confirmTime, String callbackContent, Date callbackTime) {
        this.id = id;
        this.outTradeNo = outTradeNo;
        this.orderId = orderId;
        this.alipayTradeNo = alipayTradeNo;
        this.totalAmount = totalAmount;
        this.subject = subject;
        this.paymentStatus = paymentStatus;
        this.createTime = createTime;
        this.confirmTime = confirmTime;
        this.callbackContent = callbackContent;
        this.callbackTime = callbackTime;
    }

    public PaymentInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo == null ? null : alipayTradeNo.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus == null ? null : paymentStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getCallbackContent() {
        return callbackContent;
    }

    public void setCallbackContent(String callbackContent) {
        this.callbackContent = callbackContent == null ? null : callbackContent.trim();
    }

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
    }
}