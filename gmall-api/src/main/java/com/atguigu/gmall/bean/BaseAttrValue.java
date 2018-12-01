package com.atguigu.gmall.bean;

public class BaseAttrValue {
    private Long id;

    private String valueName;

    private Long attrId;

    private String isEnabled;

    public BaseAttrValue(Long id, String valueName, Long attrId, String isEnabled) {
        this.id = id;
        this.valueName = valueName;
        this.attrId = attrId;
        this.isEnabled = isEnabled;
    }

    public BaseAttrValue() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName == null ? null : valueName.trim();
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled == null ? null : isEnabled.trim();
    }
}