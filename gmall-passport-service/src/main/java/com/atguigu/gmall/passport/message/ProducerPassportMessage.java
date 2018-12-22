package com.atguigu.gmall.passport.message;

import com.atguigu.gmall.consts.MessageConst;
import com.atguigu.gmall.utils.ActiveMQUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.jms.*;

@Component
public class ProducerPassportMessage {


    @Autowired
    private ActiveMQUtil activeMQUtil;

    public void producerMergeCartMessage(Long userId, String cartCookieValue) {

        Connection connection = null;

        Session session = null;

        MessageProducer producer = null;


        try {
            connection = activeMQUtil.getConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);

            Queue queue = session.createQueue(MessageConst.CART_MERGE_CART_QUEUE);

            producer = session.createProducer(queue);
            MapMessage mapMessage = new ActiveMQMapMessage();

            mapMessage.setLong("userId",userId);
            mapMessage.setString("cartCookieValue",cartCookieValue);
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

    public void producerFlushCacheFormCartInfoMessage(Long userId) {
        Connection connection = null;

        Session session = null;

        MessageProducer producer = null;


        try {
            connection = activeMQUtil.getConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);

            Queue queue = session.createQueue(MessageConst.CART_FLUSH_CACHE_QUEUE);

            producer = session.createProducer(queue);
            MapMessage mapMessage = new ActiveMQMapMessage();

            mapMessage.setLong("userId",userId);

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
