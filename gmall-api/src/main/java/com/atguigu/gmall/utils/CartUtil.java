package com.atguigu.gmall.utils;

import com.atguigu.gmall.bean.CartInfo;

import java.util.List;

public class CartUtil {


    /**
     * 判断在更新时是否存在于之前的Cookie，是否为一个新的购物车
     * @return 为true时说明为一个新的购物车，为false说明为一个旧从购物车就进行更新
     */
    public static Boolean ifNewCart(List<CartInfo> cartInfoList, CartInfo cartInfo) {

        boolean resultFlag =true;
        for (CartInfo info : cartInfoList) {
            if ((cartInfo.getSkuId()).equals(info.getSkuId())){
                resultFlag = false;
            }
        }
        return resultFlag;
    }
}
