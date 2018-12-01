package com.atguigu.gmall.bean;

public class SpuColor {
    private Long id;

    private Long spuId;

    private String color;

    private String fileName;

    private String isEnabled;

    public SpuColor(Long id, Long spuId, String color, String fileName, String isEnabled) {
        this.id = id;
        this.spuId = spuId;
        this.color = color;
        this.fileName = fileName;
        this.isEnabled = isEnabled;
    }

    public SpuColor() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled == null ? null : isEnabled.trim();
    }
}