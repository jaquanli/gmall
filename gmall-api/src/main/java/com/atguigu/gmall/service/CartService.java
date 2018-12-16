package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.bean.SkuInfo;

import java.util.List;

public interface CartService {
    void addToCartFromDB(String userId, SkuInfo skuInfo, String skuNum);

    List<CartInfo> getCartListFromCache(String userId);

    List<CartInfo> updateCartCheck(String isChecked, String skuId,String userId);

    Boolean mergeCart(Long id, List<CartInfo> parseArray);

    void flushCacheFormCartInfo(Long userId);

    List<CartInfo> queryCartListFormDBByUserId(String userId);

    void removeCheckedCart(String userId, List<Long> checkedCartIdList);
}
