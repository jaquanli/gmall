package com.atguigu.gmall.bean;

public class BaseTrademark {
    private Long id;

    private String tmName;

    private String logoUrl;

    private String isEnable;

    public BaseTrademark(Long id, String tmName, String logoUrl, String isEnable) {
        this.id = id;
        this.tmName = tmName;
        this.logoUrl = logoUrl;
        this.isEnable = isEnable;
    }

    public BaseTrademark() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTmName() {
        return tmName;
    }

    public void setTmName(String tmName) {
        this.tmName = tmName == null ? null : tmName.trim();
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable == null ? null : isEnable.trim();
    }
}