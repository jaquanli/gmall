package com.atguigu.gmall.bean;

public class AdBanner {
    private Long id;

    private String adDesc;

    private String fileName;

    public AdBanner(Long id, String adDesc, String fileName) {
        this.id = id;
        this.adDesc = adDesc;
        this.fileName = fileName;
    }

    public AdBanner() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdDesc() {
        return adDesc;
    }

    public void setAdDesc(String adDesc) {
        this.adDesc = adDesc == null ? null : adDesc.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
}