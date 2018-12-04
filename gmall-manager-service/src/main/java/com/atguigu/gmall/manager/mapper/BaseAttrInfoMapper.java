package com.atguigu.gmall.manager.mapper;

import com.atguigu.gmall.bean.BaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {


    BaseAttrInfo selectBaseAttrInfoByAttrNameAndCatalog3Id(
            @Param("attrName") String attrName,
            @Param("catalog3Id") Long catalog3Id);
}
