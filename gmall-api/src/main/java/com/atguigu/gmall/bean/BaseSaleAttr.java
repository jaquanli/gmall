package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class BaseSaleAttr implements Serializable {

    @Id
    private Long id;

    private String name;

    public BaseSaleAttr(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseSaleAttr() {
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

    @Override
    public String toString() {
        return "BaseSaleAttr{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}