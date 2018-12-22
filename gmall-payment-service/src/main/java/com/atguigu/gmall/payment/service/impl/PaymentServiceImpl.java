package com.atguigu.gmall.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.bean.enums.PaymentStatus;
import com.atguigu.gmall.consts.MessageConst;
import com.atguigu.gmall.payment.mapper.PaymentInfoMapper;
import com.atguigu.gmall.service.PaymentService;
import com.atguigu.gmall.utils.ActiveMQUtil;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import javax.jms.*;
import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private ActiveMQUtil activeMQUtil;

    @Autowired
    private AlipayClient alipayClient;

    /**
     * 增加支付信息
     *
     * @param paymentInfo 支付信息
     */
    @Override
    public void addPaymentInfo(PaymentInfo paymentInfo) {

        paymentInfoMapper.insertSelective(paymentInfo);
    }

    /**
     * 更新支付信息
     *
     * @param paymentInfo 支付完成后的信息
     */
    @Override
    public void updatePaymentInfo(PaymentInfo paymentInfo) {
        Example example = new Example(PaymentInfo.class);
        example.createCriteria().andEqualTo("outTradeNo", paymentInfo.getOutTradeNo());

        //这里更新应该同步redis，把之前的值放到redis中
        paymentInfoMapper.updateByExampleSelective(paymentInfo, example);
    }

    @Override
    public PaymentInfo getPaymentInfoByOutTradeNo(String outTradeNo) {

        Example example = new Example(PaymentInfo.class);
        example.createCriteria().andEqualTo("outTradeNo", outTradeNo);
        //这里获取应该从redis获取，获取不到应该从DB获取
        return paymentInfoMapper.selectOneByExample(example);
    }

    /**
     * 发送订单支付结果的消息
     *
     * @param paymentInfo 支付信息对象
     */
    @Override
    public void sendPaymentResultMessage(PaymentInfo paymentInfo, String paymentResult) {

        try {
            //获得Mq连接对象
            Connection connection = activeMQUtil.getConnection();
            //获取session对象，并开启事务功能
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            //创建队列式消息并设置名称
            Queue paymentResultQueue = session.createQueue(MessageConst.PAYMENT_RESULT_QUEUE);
            //创建一个消息提供者，将创建的消息封装
            MessageProducer producer = session.createProducer(paymentResultQueue);
            //创建一个消息类型的map用于封装消息携带的数据
            MapMessage mapMessage = new ActiveMQMapMessage();
            //支付结果
            mapMessage.setString("paymentResult", paymentResult);
            //aliPay交易号
            mapMessage.setString("trade_no", paymentInfo.getAlipayTradeNo());
            //aliPay传来的交易表单信息
            mapMessage.setString("callbackContent", paymentInfo.getCallbackContent());
            //PaymentStatus 支付状态
            mapMessage.setString("PaymentStatus", paymentInfo.getPaymentStatus());
            //支付宝回传的我们的外部订单号
            mapMessage.setString("out_trade_no", paymentInfo.getOutTradeNo());
            //将携带的数据通过消息,发送过去
            producer.send(mapMessage);
            //当支付结果不成功，进行回滚
            if (!(MessageConst.MESSAGE_SUCCESS.equals(paymentResult))) {
                session.rollback();
            } else {
                //提交事务
                System.err.println("发送订单支付结果的消息。。。。。。");
                session.commit();
            }
            //关闭消息提供者
            producer.close();
            //关闭session
            session.close();
            //关闭连接
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送延迟的支付结果检查消息
     *
     * @param outTradeNo 外部订单号
     * @param delaySec   延迟时间，秒级
     * @param checkCount 检查次数
     */
    @Override
    public void sendDelayPaymentResultMessage(String outTradeNo, int delaySec, int checkCount) {

        try {
            Connection connection = activeMQUtil.getConnection();

            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

            Queue queue = session.createQueue(MessageConst.PAYMENT_RESULT_CHECK_QUEUE);

            MessageProducer producer = session.createProducer(queue);

            MapMessage mapMessage = new ActiveMQMapMessage();

            mapMessage.setString("outTradeNo", outTradeNo);
            mapMessage.setInt("delaySec", delaySec);
            mapMessage.setInt("checkCount", checkCount);
            //设置消息为延迟消息，并设置延迟时间为10秒消费一次
            mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delaySec * 1000);
            //发送消息
            producer.send(mapMessage);
            session.commit();
            System.err.println("第【" +(4-checkCount)+"】发送了延迟队列消息........");
            producer.close();
            session.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查支付状态，支付状态为支付中的话就更新
     *
     * @param paymentInfo 从数据库中获取的对象
     * @return 更新完成的对象，或是null
     */
    @Override
    public PaymentInfo checkPaymentState(PaymentInfo paymentInfo) {

        //不为空时
        if (paymentInfo != null) {
            //当支付状态不是已支付状态
            if (!paymentInfo.getPaymentStatus().equals(PaymentStatus.PAID.getName())) {
                //当支付状态为支付中状态
                if (PaymentStatus.UNPAID.getName().equals(paymentInfo.getPaymentStatus())) {
                    //更新
                    paymentInfo.setPaymentStatus(PaymentStatus.PAID.getName());//支付状态
                    //更新
                    updatePaymentInfo(paymentInfo);
                    //返回更新后的状态
                    return getPaymentInfoByOutTradeNo(paymentInfo.getOutTradeNo());
                }
            } else {
                return paymentInfo;
            }
        }
        return paymentInfo;
    }


    /**
     * 检查aliPay的线下交易结果
     *
     * @param outTradeNo 商品外部编号
     * @return 返回交易状态等
     */
    @Override
    public PaymentStatus checkAliPaymentResult(String outTradeNo) {

        //获取支付信息
        PaymentInfo paymentInfoDB = getPaymentInfoByOutTradeNo(outTradeNo);
        //判断是否为已支付
        if (PaymentStatus.PAID.getName().equals(paymentInfoDB.getPaymentStatus())) {
            return PaymentStatus.PAID;
        }

        //此时不是已支付状态

        //生成aliPay线下交易结果查询对象
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //封装参数
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(outTradeNo);
        //塞入请求
        request.setBizModel(model);

        //发送请求，获得响应
        AlipayTradeQueryResponse response = null;
        try {
            System.err.println("向AliPay发送查询请求.................");
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //判断是否调用成功
        if (response.isSuccess()) {

            System.out.println("调用成功..................");
            int updateCount = 0;

            if (0 == updateCount){
                //设置要更新的支付信息值
                //aliPay的交易号
                paymentInfoDB.setAlipayTradeNo(response.getTradeNo());
                //回调体
                paymentInfoDB.setCallbackContent(response.getBody());
                //回调时间
                paymentInfoDB.setCallbackTime(new Date());
                //更新,这里更新时因为，在用户创建了交易，但是一直未付款的状态
                updatePaymentInfo(paymentInfoDB);
                updateCount ++;
            }


            //判断支付状态,为正在支付状态
            if ("WAIT_BUYER_PAY".equals(response.getTradeStatus())) {
                System.err.println("交易已创建，等待付款..................");
                return PaymentStatus.UNPAID;
            }else {

                //判断支付状态,为支付状态已关闭
                if ("TRADE_CLOSED".equals(response.getTradeStatus())) {
                    System.err.println("未付款交易超时，或支付完成后全额退款..................");
                    //此时为支付关闭的状态，需要更新支付信息

                    //支付状态
                    paymentInfoDB.setPaymentStatus(PaymentStatus.ClOSED.getName());
                    System.err.println("更新支付信息..................");
                    //更新
                    updatePaymentInfo(paymentInfoDB);
                    //返回支付状态
                    return PaymentStatus.ClOSED;
                }
                //判断支付状态,为支付状态已支付
                if ("TRADE_SUCCESS".equals(response.getTradeStatus())) {
                    //此时为支付成功的状态，需要更新支付信息
                    System.err.println("交易支付成功..................");
                    //支付状态
                    paymentInfoDB.setPaymentStatus(PaymentStatus.PAID.getName());

                    System.err.println("更新支付信息..................");
                    //更新
                    updatePaymentInfo(paymentInfoDB);
                    //返回支付状态
                    return PaymentStatus.PAID;
                }
            }

        } else {
            System.out.println("调用失败..................");
            //返回支付交易还未创建，或产生异常
            System.err.println("交易还未创建，或产生异常..................");
            return PaymentStatus.PAY_FAIL;
        }

        System.err.println("交易已创建，等待付款..................");
        //返回未支付状态
        return PaymentStatus.UNPAID;
    }
}

