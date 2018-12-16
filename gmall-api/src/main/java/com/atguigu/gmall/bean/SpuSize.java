package com.atguigu.gmall.bean;
/**
 * 暂用不着
 */
public class SpuSize {
    private Long id;

    private Long spuId;

    private String spuSize;

    private String isEnabled;

    public SpuSize(Long id, Long spuId, String spuSize, String isEnabled) {
        this.id = id;
        this.spuId = spuId;
        this.spuSize = spuSize;
        this.isEnabled = isEnabled;
    }

    public SpuSize() {
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

    public String getSpuSize() {
        return spuSize;
    }

    public void setSpuSize(String spuSize) {
        this.spuSize = spuSize == null ? null : spuSize.trim();
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled == null ? null : isEnabled.trim();
    }
}