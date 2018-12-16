package com.atguigu.gmall.bean;
/**
 * 暂用不着
 */
public class WareInfo {
    private Long id;

    private String name;

    private String address;

    private String areacode;

    public WareInfo(Long id, String name, String address, String areacode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.areacode = areacode;
    }

    public WareInfo() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }
}