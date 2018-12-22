package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.bean.enums.PaymentStatus;

import java.util.Map;

public interface PaymentService {
    void addPaymentInfo(PaymentInfo paymentInfo);

    void updatePaymentInfo(PaymentInfo paymentInfo);

    PaymentInfo getPaymentInfoByOutTradeNo(String outTradeNo);

    void sendPaymentResultMessage(PaymentInfo paymentInfo,String paymentResult);

    void sendDelayPaymentResultMessage(String outTradeNo,int delaySec,int checkCount);

    PaymentStatus checkAliPaymentResult(String outTradeNo);

    PaymentInfo checkPaymentState(PaymentInfo paymentInfo);
}
