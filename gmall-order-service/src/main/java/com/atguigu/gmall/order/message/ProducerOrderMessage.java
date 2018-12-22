package com.atguigu.gmall.order.message;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.OrderInfo;
import com.atguigu.gmall.consts.MessageConst;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.utils.ActiveMQUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.jms.*;
import java.util.List;

@Component
public class ProducerOrderMessage {

    @Autowired
    private ActiveMQUtil activeMQUtil;

    @Autowired
    private OrderService orderService;

    /**
     * 发送订单操作成功的消息
     *
     * @param outTradeNo  外部订单号
     * @param orderResult 订单操作的结果
     */
    void sendOrderResultMessage(String outTradeNo, String orderResult) {

        Session session = null;

        try {
            Connection connection = activeMQUtil.getConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);

            Queue orderResultQueue = session.createQueue(MessageConst.ORDER_RESULT_QUEUE);

            MessageProducer producer = session.createProducer(orderResultQueue);
            //获取orderInfo对象
            OrderInfo orderInfo = orderService.getOrderByOutTradeNo(outTradeNo);

            TextMessage textMessage = new ActiveMQTextMessage();
            //转换到jSON对象，塞入到消息
            textMessage.setText(JSON.toJSONString(orderInfo));

            producer.send(textMessage);

            if (("SUCCESS".equals(orderResult))) {
                System.err.println("发送订单更新成功的消息。。。。。。等待库存业务接收消息。。");
                session.commit();
                //关闭消息提供者
            }
            producer.close();
            //关闭session
            session.close();
            //关闭连接
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 发送删除购物车勾选的商品消息
     * @param userId
     * @param checkedCartIdList
     */
    public void sendRemoveCheckedCartMessage(String userId, List<Long> checkedCartIdList) {

        Connection connection =null;
        Session session = null;
        MessageProducer producer = null;
        try {
            connection = activeMQUtil.getConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);

            Queue queue = session.createQueue(MessageConst.CART_REMOVE_CHECKED_QUEUE);

            producer = session.createProducer(queue);

            MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setString("userId",userId);
            mapMessage.setObject("checkedCartIdList",checkedCartIdList);

            producer.send(mapMessage);

            session.commit();

            producer.close();

            session.close();

            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.rollback();
                producer.close();

                session.close();

                connection.close();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }


    }
}
