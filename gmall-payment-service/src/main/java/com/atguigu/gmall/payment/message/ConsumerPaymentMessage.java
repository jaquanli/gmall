package com.atguigu.gmall.payment.message;

import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.bean.enums.PaymentStatus;
import com.atguigu.gmall.consts.MessageConst;
import com.atguigu.gmall.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

@Component
public class ConsumerPaymentMessage {

    @Autowired
    private PaymentService paymentService;

    /**
     * 延迟支付检查队列消息的消费端
     *
     * @param mapMessage 生产者发送的消息所携带的数据
     * @throws JMSException
     */
    @JmsListener(destination = MessageConst.PAYMENT_RESULT_CHECK_QUEUE, containerFactory = "jmsQueueListener")
    public void consumeCheckPaymentResult(MapMessage mapMessage) throws JMSException {

        String outTradeNo = mapMessage.getString("outTradeNo");
        int delaySec = mapMessage.getInt("delaySec");
        int checkCount = mapMessage.getInt("checkCount");
        //调用支付宝线下查询接口，根据支付结果进行更新
        System.err.println("调用支付宝线下查询接口.....第【" + (4-checkCount) +"】次，每次时间间隔为【" + delaySec + "】秒");
        //
        PaymentStatus paymentStatus = paymentService.checkAliPaymentResult(outTradeNo);
        //减少次数
        checkCount --;

        System.err.println("调用支付宝线下查询接口.....还剩余【" + checkCount +"】次");
        //如果一直未支付，则再次进行发送消息，直到支付接口接收到支付具体的结果后结束，或者是检查次数超过也会支付失败
        if (checkCount > 0 && (paymentStatus == PaymentStatus.UNPAID || paymentStatus ==PaymentStatus.PAY_FAIL)) {
            //重新发送消息
            paymentService.sendDelayPaymentResultMessage(outTradeNo, delaySec, checkCount);
        }
        //当次数小于0时，支付信息为关闭，订单也为关闭
        if (checkCount <= 0 && (paymentStatus == PaymentStatus.UNPAID || paymentStatus ==PaymentStatus.PAY_FAIL)){

            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setPaymentStatus(PaymentStatus.ClOSED.getName());
            paymentInfo.setOutTradeNo(outTradeNo);
            paymentService.updatePaymentInfo(paymentInfo);

            //发支付结果的消息队列消息
            paymentService.sendPaymentResultMessage(paymentInfo,MessageConst.MESSAGE_SUCCESS);

            System.err.println("支付超时，支付状态更新为关闭.................");
        }
        //支付成功状态，发起订单消息
        if (paymentStatus == PaymentStatus.PAID){
            //查询更新完成后的支付信息
            PaymentInfo paymentInfo = paymentService.getPaymentInfoByOutTradeNo(outTradeNo);
            //发支付结果的消息队列消息

            paymentService.sendPaymentResultMessage(paymentInfo,MessageConst.MESSAGE_SUCCESS);
        }
    }
}
