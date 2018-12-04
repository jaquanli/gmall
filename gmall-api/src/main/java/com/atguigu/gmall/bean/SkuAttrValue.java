package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class SkuAttrValue implements Serializable {

    @Id
    private Long id;

    private Long attrId;

    private Long valueId;

    private Long skuId;

    @Override
    public String toString() {
        return "SkuAttrValue{" +
                "id=" + id +
                ", attrId=" + attrId +
                ", valueId=" + valueId +
                ", skuId=" + skuId +
                '}';
    }

    public SkuAttrValue(Long id, Long attrId, Long valueId, Long skuId) {
        this.id = id;
        this.attrId = attrId;
        this.valueId = valueId;
        this.skuId = skuId;
    }

    public SkuAttrValue() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}