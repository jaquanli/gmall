package com.atguigu.gmall.cart.mapper;

import com.atguigu.gmall.bean.CartInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CartInfoMapper extends Mapper<CartInfo> {
    int deleteCartInfoByIds(@Param("checkedCartIdList") List<Long> checkedCartIdList);
}