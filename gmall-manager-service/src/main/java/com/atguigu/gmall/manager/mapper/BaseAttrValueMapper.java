package com.atguigu.gmall.manager.mapper;

import com.atguigu.gmall.bean.BaseAttrValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BaseAttrValueMapper extends Mapper<BaseAttrValue> {

    BaseAttrValue selectBaseAttrValueByValueNameAndAttrInfoId(
            @Param("valueName") String valueName,
            @Param("attrInfoId") Long attrInfoId);
}
