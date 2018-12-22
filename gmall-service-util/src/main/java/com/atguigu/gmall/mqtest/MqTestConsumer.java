package com.atguigu.gmall.mqtest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class MqTestConsumer {

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://120.79.26.10:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("test11");
            MessageConsumer consumer = session.createConsumer(queue);
            // 接收消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    // 打印结果
                    TextMessage textMessage = (TextMessage) message;
                    String text= null;
                    try {
                        text = textMessage.getText();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    System.out.println(text);
                }
            });
            // 等待接收消息
            System.in.read();
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }

    }
}
