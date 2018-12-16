package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String consignee;

    private BigDecimal totalAmount;

    private String orderStatus;

    private Long userId;

    private String paymentWay;

    private Date expectDeliveryTime;

    private String deliveryAddress;

    private String orderComment;

    private String outTradeNo;

    private String tradeBody;

    private Date createTime;

    private Date expireTime;

    private String wareStatus;

    private Long parentOrderId;

    private String processStatus;

    private String trackingNo;

    private String consigneeTel;

    private List<OrderDetail> orderDetailList;

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", consignee='" + consignee + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderStatus='" + orderStatus + '\'' +
                ", userId=" + userId +
                ", paymentWay='" + paymentWay + '\'' +
                ", expectDeliveryTime=" + expectDeliveryTime +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", orderComment='" + orderComment + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", tradeBody='" + tradeBody + '\'' +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", wareStatus='" + wareStatus + '\'' +
                ", parentOrderId=" + parentOrderId +
                ", processStatus='" + processStatus + '\'' +
                ", trackingNo='" + trackingNo + '\'' +
                ", consigneeTel='" + consigneeTel + '\'' +
                ", orderDetailList=" + orderDetailList +
                '}';
    }

    public OrderInfo(Long id, String consignee, BigDecimal totalAmount, String orderStatus, Long userId, String paymentWay, Date expectDeliveryTime, String deliveryAddress, String orderComment, String outTradeNo, String tradeBody, Date createTime, Date expireTime, String wareStatus, Long parentOrderId, String processStatus, String trackingNo, String consigneeTel, List<OrderDetail> orderDetailList) {
        this.id = id;
        this.consignee = consignee;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.paymentWay = paymentWay;
        this.expectDeliveryTime = expectDeliveryTime;
        this.deliveryAddress = deliveryAddress;
        this.orderComment = orderComment;
        this.outTradeNo = outTradeNo;
        this.tradeBody = tradeBody;
        this.createTime = createTime;
        this.expireTime = expireTime;
        this.wareStatus = wareStatus;
        this.parentOrderId = parentOrderId;
        this.processStatus = processStatus;
        this.trackingNo = trackingNo;
        this.consigneeTel = consigneeTel;
        this.orderDetailList = orderDetailList;
    }

    public OrderInfo() {
        super();
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay == null ? null : paymentWay.trim();
    }

    public Date getExpectDeliveryTime() {
        return expectDeliveryTime;
    }

    public void setExpectDeliveryTime(Date expectDeliveryTime) {
        this.expectDeliveryTime = expectDeliveryTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment == null ? null : orderComment.trim();
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public String getTradeBody() {
        return tradeBody;
    }

    public void setTradeBody(String tradeBody) {
        this.tradeBody = tradeBody == null ? null : tradeBody.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getWareStatus() {
        return wareStatus;
    }

    public void setWareStatus(String wareStatus) {
        this.wareStatus = wareStatus == null ? null : wareStatus.trim();
    }

    public Long getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(Long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus == null ? null : processStatus.trim();
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo == null ? null : trackingNo.trim();
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel == null ? null : consigneeTel.trim();
    }
}