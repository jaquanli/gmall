package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class BaseCatalog3 implements Serializable {

    @Id
    private Long id;

    private String name;

    private Long catalog2Id;

    public BaseCatalog3(Long id, String name, Long catalog2Id) {
        this.id = id;
        this.name = name;
        this.catalog2Id = catalog2Id;
    }

    public BaseCatalog3() {
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

    public Long getCatalog2Id() {
        return catalog2Id;
    }

    public void setCatalog2Id(Long catalog2Id) {
        this.catalog2Id = catalog2Id;
    }

    @Override
    public String toString() {
        return "BaseCatalog3{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", catalog2Id=" + catalog2Id +
                '}';
    }
}