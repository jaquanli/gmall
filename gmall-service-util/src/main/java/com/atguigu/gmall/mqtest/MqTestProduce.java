package com.atguigu.gmall.mqtest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MqTestProduce {

    public static void main(String[] args) {

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://120.79.26.10:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("test11");
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage("我是测试");
            producer.send(textMessage);
            producer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
