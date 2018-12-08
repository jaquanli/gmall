package com.atguigu.gmall.bean;

import java.io.Serializable;


/**
 * 匹配SkuAttrValue的elasticsearch的JavaBean
 */
public class SkuLsAttrValue implements Serializable {


    private String valueId;

    public SkuLsAttrValue() {
    }

    public SkuLsAttrValue(String valueId) {
        this.valueId = valueId;
    }

    @Override
    public String toString() {
        return "SkuLsAttrValue{" +
                "valueId='" + valueId + '\'' +
                '}';
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }


}
