package com.atguigu.gmall.passport.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.passport.message.ProducerPassportMessage;
import com.atguigu.gmall.service.PassportService;
import com.atguigu.gmall.utils.ActiveMQUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PassportServiceImpl implements PassportService {

    @Autowired
    private ActiveMQUtil activeMQUtil;

    @Autowired
    private ProducerPassportMessage producerPassportMessage;

    /**
     * 调用接口发送消息
     * @param userId 用户id
     * @param cartCookieValue 用户购物车Cookie的值
     */
    @Override
    public void sendMergeCartMessage(Long userId, String cartCookieValue) {

        producerPassportMessage.producerMergeCartMessage(userId,cartCookieValue);

    }

    /**
     * 调用接口发送刷新缓存的消息
     * @param userId
     */
    @Override
    public void sendFlushCacheFormCartInfoMessage(Long userId) {


        producerPassportMessage.producerFlushCacheFormCartInfoMessage(userId);

    }
}
