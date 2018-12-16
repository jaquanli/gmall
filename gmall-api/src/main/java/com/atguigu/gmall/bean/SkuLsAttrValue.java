package com.atguigu.gmall.bean;

import java.io.Serializable;


/**
 * 匹配SkuAttrValue的elasticsearch的JavaBean
 */
public class SkuLsAttrValue implements Serializable {


    private Long valueId;

    public SkuLsAttrValue() {
    }

    public SkuLsAttrValue(Long valueId) {
        this.valueId = valueId;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    @Override
    public String toString() {
        return "SkuLsAttrValue{" +
                "valueId=" + valueId +
                '}';
    }
}
