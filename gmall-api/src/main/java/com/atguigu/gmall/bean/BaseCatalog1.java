package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class BaseCatalog1 implements Serializable {
    @Id
    private Long id;

    private String name;

    @Override
    public String toString() {
        return "BaseCatalog1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public BaseCatalog1(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseCatalog1() {
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
}