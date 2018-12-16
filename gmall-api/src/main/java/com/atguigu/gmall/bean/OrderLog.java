package com.atguigu.gmall.bean;

import java.util.Date;

/**
 * 暂用不着
 */
public class OrderLog {
    private Long id;

    private Long orderId;

    private Date operateDate;

    private String logComment;

    public OrderLog(Long id, Long orderId, Date operateDate, String logComment) {
        this.id = id;
        this.orderId = orderId;
        this.operateDate = operateDate;
        this.logComment = logComment;
    }

    public OrderLog() {
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

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getLogComment() {
        return logComment;
    }

    public void setLogComment(String logComment) {
        this.logComment = logComment == null ? null : logComment.trim();
    }
}