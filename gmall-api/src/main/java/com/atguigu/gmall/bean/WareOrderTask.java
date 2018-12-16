package com.atguigu.gmall.bean;

import java.util.Date;
/**
 * 暂用不着
 */
public class WareOrderTask {
    private Long id;

    private Long orderId;

    private String consignee;

    private String consigneeTel;

    private String deliveryAddress;

    private String orderComment;

    private String paymentWay;

    private String taskStatus;

    private String orderBody;

    private String trackingNo;

    private Date createTime;

    private Long wareId;

    private String taskComment;

    public WareOrderTask(Long id, Long orderId, String consignee, String consigneeTel, String deliveryAddress, String orderComment, String paymentWay, String taskStatus, String orderBody, String trackingNo, Date createTime, Long wareId, String taskComment) {
        this.id = id;
        this.orderId = orderId;
        this.consignee = consignee;
        this.consigneeTel = consigneeTel;
        this.deliveryAddress = deliveryAddress;
        this.orderComment = orderComment;
        this.paymentWay = paymentWay;
        this.taskStatus = taskStatus;
        this.orderBody = orderBody;
        this.trackingNo = trackingNo;
        this.createTime = createTime;
        this.wareId = wareId;
        this.taskComment = taskComment;
    }

    public WareOrderTask() {
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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel == null ? null : consigneeTel.trim();
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

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay == null ? null : paymentWay.trim();
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody == null ? null : orderBody.trim();
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo == null ? null : trackingNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getWareId() {
        return wareId;
    }

    public void setWareId(Long wareId) {
        this.wareId = wareId;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment == null ? null : taskComment.trim();
    }
}