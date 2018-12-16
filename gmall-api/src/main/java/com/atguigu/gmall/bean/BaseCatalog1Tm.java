package com.atguigu.gmall.bean;
/**
 * 暂用不着
 */
public class BaseCatalog1Tm {
    private Long id;

    private Long tmId;

    private String catalog1Id;

    public BaseCatalog1Tm(Long id, Long tmId, String catalog1Id) {
        this.id = id;
        this.tmId = tmId;
        this.catalog1Id = catalog1Id;
    }

    public BaseCatalog1Tm() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmId() {
        return tmId;
    }

    public void setTmId(Long tmId) {
        this.tmId = tmId;
    }

    public String getCatalog1Id() {
        return catalog1Id;
    }

    public void setCatalog1Id(String catalog1Id) {
        this.catalog1Id = catalog1Id == null ? null : catalog1Id.trim();
    }
}