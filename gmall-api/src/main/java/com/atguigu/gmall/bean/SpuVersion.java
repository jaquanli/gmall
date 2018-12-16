package com.atguigu.gmall.bean;
/**
 * 暂用不着
 */
public class SpuVersion {
    private Long id;

    private Long spuId;

    private String spuVersion;

    private String isEnabled;

    public SpuVersion(Long id, Long spuId, String spuVersion, String isEnabled) {
        this.id = id;
        this.spuId = spuId;
        this.spuVersion = spuVersion;
        this.isEnabled = isEnabled;
    }

    public SpuVersion() {
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

    public String getSpuVersion() {
        return spuVersion;
    }

    public void setSpuVersion(String spuVersion) {
        this.spuVersion = spuVersion == null ? null : spuVersion.trim();
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled == null ? null : isEnabled.trim();
    }
}