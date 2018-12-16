package com.atguigu.gmall.bean;

import java.util.Date;
/**
 * 暂用不着
 */
public class OrderLogistics {
    private Long id;

    private Long orderId;

    private String logisticsStatus;

    private String logisticsNo;

    private Date createTime;

    public OrderLogistics(Long id, Long orderId, String logisticsStatus, String logisticsNo, Date createTime) {
        this.id = id;
        this.orderId = orderId;
        this.logisticsStatus = logisticsStatus;
        this.logisticsNo = logisticsNo;
        this.createTime = createTime;
    }

    public OrderLogistics() {
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

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus == null ? null : logisticsStatus.trim();
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}