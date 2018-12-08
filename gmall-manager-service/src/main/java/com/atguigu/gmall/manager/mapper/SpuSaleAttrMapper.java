package com.atguigu.gmall.manager.mapper;

import com.atguigu.gmall.bean.SpuSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {
    void insertSpuSaleAttrList(@Param("spuSaleAttrList") List<SpuSaleAttr> spuSaleAttrList);

    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("spuId") Long spuId,@Param("skuId")Long skuId);
}
