package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

public class SpuInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String spuName;

    private String description;

    private Long catalog3Id;

    private Long tmId;

    private List<SpuSaleAttr> spuSaleAttrList;

    private List<SpuImage> spuImageList;

    @Override
    public String toString() {
        return "SpuInfo{" +
                "id=" + id +
                ", spuName='" + spuName + '\'' +
                ", description='" + description + '\'' +
                ", catalog3Id=" + catalog3Id +
                ", tmId=" + tmId +
                ", spuSaleAttrList=" + spuSaleAttrList +
                ", spuImageList=" + spuImageList +
                '}';
    }

    public SpuInfo(String spuName, String description, Long catalog3Id, Long tmId, List<SpuSaleAttr> spuSaleAttrList, List<SpuImage> spuImageList) {
        this.spuName = spuName;
        this.description = description;
        this.catalog3Id = catalog3Id;
        this.tmId = tmId;
        this.spuSaleAttrList = spuSaleAttrList;
        this.spuImageList = spuImageList;
    }

    public SpuInfo() {
        super();
    }

    public List<SpuImage> getSpuImageList() {
        return spuImageList;
    }

    public void setSpuImageList(List<SpuImage> spuImageList) {
        this.spuImageList = spuImageList;
    }

    public List<SpuSaleAttr> getSpuSaleAttrList() {
        return spuSaleAttrList;
    }

    public void setSpuSaleAttrList(List<SpuSaleAttr> spuSaleAttrList) {
        this.spuSaleAttrList = spuSaleAttrList;
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