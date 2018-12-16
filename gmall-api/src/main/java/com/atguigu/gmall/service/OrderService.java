package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.OrderInfo;

public interface OrderService {
    OrderInfo creatCheckOrderInfo(String userId);

    Boolean isTradeCode(String userId,String tradeCode);

    void addTempTradeCode(String userId,String tradeCode);

    OrderInfo creatOrder(String userId,OrderInfo orderInfo);
}
