package com.atguigu.gmall.consts;

public interface MessageConst {

    String PAYMENT_RESULT_QUEUE = "PAYMENT_RESULT_QUEUE";//发送支付结果的消息名称
    String ORDER_RESULT_QUEUE = "ORDER_RESULT_QUEUE";//发送订单操作结果的消息名
    String MESSAGE_SUCCESS = "SUCCESS";//消息的结果状态
    String MESSAGE_FAILED = "FAILED";//消息的结果状态
    String PAYMENT_RESULT_CHECK_QUEUE = "PAYMENT_RESULT_CHECK_QUEUE";//发送延迟队列支付结果检查的消息名称
    String CART_REMOVE_CHECKED_QUEUE = "CART_REMOVE_CHECKED_QUEUE";//发送删除购物车勾选的数据的消息名称
    String CART_MERGE_CART_QUEUE = "CART_MERGE_CART_QUEUE";
    String CART_FLUSH_CACHE_QUEUE = "CART_FLUSH_CACHE_QUEUE";
}
