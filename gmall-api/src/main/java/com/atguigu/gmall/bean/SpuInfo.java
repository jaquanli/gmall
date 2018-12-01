package com.atguigu.gmall.bean;

public class SpuInfo {
    private Long id;

    private String spuName;

    private String description;

    private Long catalog3Id;

    private Long tmId;

    public SpuInfo(Long id, String spuName, String description, Long catalog3Id, Long tmId) {
        this.id = id;
        this.spuName = spuName;
        this.description = description;
        this.catalog3Id = catalog3Id;
        this.tmId = tmId;
    }

    public SpuInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName == null ? null : spuName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Long catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public Long getTmId() {
        return tmId;
    }

    public void setTmId(Long tmId) {
        this.tmId = tmId;
    }
}