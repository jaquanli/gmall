package com.atguigu.gmall.manager.mapper;

import com.atguigu.gmall.bean.BaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {


    BaseAttrInfo selectBaseAttrInfoByAttrNameAndCatalog3Id(
            @Param("attrName") String attrName,
            @Param("catalog3Id") Long catalog3Id);

    List<BaseAttrInfo> selectBaseAttrInfoByValueIds(@Param("valueIds") Set<String> valueIds);
}
