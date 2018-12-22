package com.atguigu.gmall.confs;

import com.atguigu.gmall.utils.ActiveMQUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.Session;

/**
 * ActiveMq的配置类，用于传入配置参数并
 */
@Configuration
public class ActiveMQConfig {


    @Value("${spring.activemq.broker-url:disabled}")
    String brokerURL;

    @Value("${activemq.listener.enable:disabled}")
    String listenerEnable;

    /**
     * 将Active的工具类配置到Spring的IOC容器中，受Spring管理
     * @return 工具类的具体实例
     */
    @Bean
    public ActiveMQUtil getActiveMQUtil() {
        if("disabled".equals(brokerURL)){
            return null;
        }
        ActiveMQUtil activeMQUtil = new ActiveMQUtil();
        activeMQUtil.init(brokerURL);
        return activeMQUtil;
    }


    /**
     * 将ActiveMQ的监听创建配置到Spring的IOC容器中，受Spring管理
     * @param activeMQConnectionFactory Active的连接产生工厂
     * @return
     */
    @Bean(name = "jmsQueueListener")
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {

        if("disabled".equals(listenerEnable)){
            return null;
        }

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);

        factory.setSessionTransacted(false);//设置不开启事务

        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        factory.setConcurrency("5");
        factory.setRecoveryInterval(5000L);

        return factory;

    }

    /**
     * Active的连接产生工厂，获取到连接对象
     * @return 具有连接属性的对象
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory ( ){
        //通过地址获取到连接
        return new ActiveMQConnectionFactory(brokerURL);
    }

}