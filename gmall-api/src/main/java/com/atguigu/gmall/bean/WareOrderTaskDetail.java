package com.atguigu.gmall.bean;

public class WareOrderTaskDetail {
    private Long id;

    private Long skuId;

    private String skuName;

    private Integer skuNums;

    private Long taskId;

    private Integer skuNum;

    public WareOrderTaskDetail(Long id, Long skuId, String skuName, Integer skuNums, Long taskId, Integer skuNum) {
        this.id = id;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuNums = skuNums;
        this.taskId = taskId;
        this.skuNum = skuNum;
    }

    public WareOrderTaskDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getSkuNums() {
        return skuNums;
    }

    public void setSkuNums(Integer skuNums) {
        this.skuNums = skuNums;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }
}