package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class BaseCatalog2 implements Serializable {

    @Id
    private Long id;

    private String name;

    private Long catalog1Id;

    @Override
    public String toString() {
        return "BaseCatalog2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", catalog1Id=" + catalog1Id +
                '}';
    }

    public BaseCatalog2(Long id, String name, Long catalog1Id) {
        this.id = id;
        this.name = name;
        this.catalog1Id = catalog1Id;
    }

    public BaseCatalog2() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCatalog1Id() {
        return catalog1Id;
    }

    public void setCatalog1Id(Long catalog1Id) {
        this.catalog1Id = catalog1Id;
    }
}