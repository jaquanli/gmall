package com.atguigu.gmall.cart.message;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.consts.MessageConst;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.utils.ActiveMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.List;

@Component
public class ConsumerCartMessage {

    @Autowired
    private ActiveMQUtil activeMQUtil;

    @Autowired
    private CartService cartService;

    /**
     * 消费删除勾选状态下的购物车数据消息，并调用具体实现删除的业务
     * @param mapMessage
     * @throws JMSException
     */
    @JmsListener(destination = MessageConst.CART_REMOVE_CHECKED_QUEUE,containerFactory = "jmsQueueListener")
    public  void consumerRemoveCheckedCartMessage(MapMessage mapMessage) throws JMSException {

        String userId = mapMessage.getString("userId");
        List<Long> checkedCartIdList = (List<Long>) mapMessage.getObject("checkedCartIdList");

        cartService.removeCheckedCart(userId,checkedCartIdList);

    }

    /**
     * 合并购物车的消息消费
     * @param mapMessage
     * @throws JMSException
     */
    @JmsListener(destination = MessageConst.CART_MERGE_CART_QUEUE,containerFactory = "jmsQueueListener")
    public  void  consumerMergeCartMessage(MapMessage mapMessage) throws JMSException {

        String cartCookieValue = mapMessage.getString("cartCookieValue");

        long userId = mapMessage.getLong("userId");

        List<CartInfo> cartInfoList = JSON.parseArray(cartCookieValue, CartInfo.class);
        Boolean aBoolean = cartService.mergeCart(userId, cartInfoList);

    }

    /**
     * 刷新缓存的消息消费
     * @param mapMessage
     * @throws JMSException
     */
    @JmsListener(destination = MessageConst.CART_FLUSH_CACHE_QUEUE,containerFactory = "jmsQueueListener")
    public  void  consumerFlushCacheFormCartInfoMessage(MapMessage mapMessage) throws JMSException {

        long userId = mapMessage.getLong("userId");

        cartService.flushCacheFormCartInfo(userId);


    }
}
