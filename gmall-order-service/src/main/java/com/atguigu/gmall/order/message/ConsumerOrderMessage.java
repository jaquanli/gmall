package com.atguigu.gmall.order.message;

import com.atguigu.gmall.bean.OrderInfo;
import com.atguigu.gmall.bean.enums.ProcessStatus;
import com.atguigu.gmall.consts.MessageConst;
import com.atguigu.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

@Component
public class ConsumerOrderMessage {


    @Autowired
    private OrderService orderService;

    @Autowired
    private ProducerOrderMessage producerOrderMessage;

    /**
     * 创建支付成功消息监听，
     * @param mapMessage 消息带来的数据
     * @throws JMSException
     */
    @JmsListener(destination = MessageConst.PAYMENT_RESULT_QUEUE,containerFactory = "jmsQueueListener")
    public void consumerPaymentResult(MapMessage mapMessage) throws JMSException {
        System.err.println("订单支付成功的消息成功接收到。。。。。。，要更新订单信息");
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setTrackingNo(mapMessage.getString("trade_no"));
        //orderInfo.setTradeBody(mapMessage.getString("callbackContent"));
        orderInfo.setOutTradeNo(mapMessage.getString("out_trade_no"));
        //获取支付结果
        String paymentResult = mapMessage.getString("paymentResult");
        //支付状态
        String paymentStatus = mapMessage.getString("PaymentStatus");
        //定义一个订单结果
        String orderResult;
        if ("SUCCESS".equals(paymentResult)){
            orderInfo.setOrderStatus(paymentStatus);
            orderInfo.setProcessStatus(paymentStatus);
            System.err.println("更新订单信息。。。");
            orderService.updateOrderInfo(orderInfo);
            orderResult = MessageConst.MESSAGE_SUCCESS;
        }else {
            orderInfo.setProcessStatus(ProcessStatus.PAY_FAIL.getComment());
            orderService.updateOrderInfo(orderInfo);
            orderResult = MessageConst.MESSAGE_FAILED;
        }
        //发送订单的操作结果的消息
        producerOrderMessage.sendOrderResultMessage(orderInfo.getOutTradeNo(),orderResult);

    }
}
