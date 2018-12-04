package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class SpuImage implements Serializable {

    @Id
    private Long id;

    private Long spuId;

    private String imgName;

    private String imgUrl;

    public SpuImage(Long id, Long spuId, String imgName, String imgUrl) {
        this.id = id;
        this.spuId = spuId;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public SpuImage() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }
}