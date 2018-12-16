package com.atguigu.gmall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.bean.OrderDetail;
import com.atguigu.gmall.bean.OrderInfo;
import com.atguigu.gmall.bean.enums.OrderStatus;
import com.atguigu.gmall.bean.enums.PaymentStatus;
import com.atguigu.gmall.bean.enums.PaymentWay;
import com.atguigu.gmall.bean.enums.ProcessStatus;
import com.atguigu.gmall.consts.WebConst;
import com.atguigu.gmall.order.mapper.OrderDetailMapper;
import com.atguigu.gmall.order.mapper.OrderInfoMapper;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private String orderTradeCodeKey;

    @Reference
    private CartService cartService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public OrderInfo creatCheckOrderInfo(String userId) {
        OrderInfo orderInfo = new OrderInfo();
        //这里应该获取数据库中真实的数据，时间原因先按照以前写法实现
        List<CartInfo> cartList = cartService.getCartListFromCache(userId);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        BigDecimal orderTotalAmount = new BigDecimal(0);
        for (CartInfo cartInfo : cartList) {
            if ("1".equals(cartInfo.getIsChecked())) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setSkuId(cartInfo.getSkuId());
                orderDetail.setSkuName(cartInfo.getSkuName());
                orderDetail.setSkuNum(String.valueOf(cartInfo.getSkuNum()));
                orderDetail.setImgUrl(cartInfo.getImgUrl());
                orderDetail.setOrderPrice(cartInfo.getCartPrice());
                orderDetailList.add(orderDetail);
                BigDecimal cartPrice = cartInfo.getCartPrice();
                orderTotalAmount = orderTotalAmount.add(cartPrice);
            }
        }
        orderInfo.setTotalAmount(orderTotalAmount);
        orderInfo.setOrderDetailList(orderDetailList);

        return orderInfo;
    }


    /**
     * 将交易码保存到redis
     *
     * @param userId    用户id
     * @param tradeCode 交易码
     */
    @Override
    public void addTempTradeCode(String userId, String tradeCode) {
        orderTradeCodeKey = "order:" + userId + ":tradeCode";
        Jedis jedis = redisUtil.getJedis();
        jedis.set(orderTradeCodeKey, String.valueOf(tradeCode));
        jedis.expire(orderTradeCodeKey, WebConst.TRADE_CODE_OUT_TIME);

    }


    /**
     * 判断交易码是否存在
     *
     * @param userId    用户id
     * @param tradeCode 交易码
     * @return 是否存在的结果
     */
    @Override
    public Boolean isTradeCode(String userId, String tradeCode) {
        orderTradeCodeKey = "order:" + userId + ":tradeCode";
        Jedis jedis = redisUtil.getJedis();

        String tradeCodeFormCache = jedis.get(orderTradeCodeKey);
        //当tradeCode不为空且与redis中的数据一致时，返回true，其余情况都返回false
        if (StringUtils.isNotBlank(tradeCodeFormCache) && tradeCodeFormCache.equals(tradeCode)) {
            jedis.del(orderTradeCodeKey);
            return true;
        }
        //关闭
        jedis.close();

        return false;
    }

    /**
     * 创建真实的订单信息，并返回数据用于支付业务
     *
     * @param userId    用户id，用于查询数据库中购物车勾选的数据
     * @param orderInfo 部分可用参数
     * @return 真实的订单信息
     */
    @Override
    public OrderInfo creatOrder(String userId, OrderInfo orderInfo) {
        //获得数据库购物车强一致性数据
        List<CartInfo> cartInfoList = cartService.queryCartListFormDBByUserId(userId);
        //创建OrderInfo对象用于封装真实订单数据
        OrderInfo orderReal = new OrderInfo();
        if (cartInfoList != null) {
            //收货人
            orderReal.setConsignee(orderInfo.getConsignee());
            //联系电话
            orderReal.setConsigneeTel(orderInfo.getConsigneeTel());
            //订单状态
            orderReal.setOrderStatus(OrderStatus.UNPAID.getComment());
            //userId
            orderReal.setUserId(Long.parseLong(userId));
            //支付类型
            switch (orderInfo.getPaymentWay()) {
                case "ONLINE":
                    orderReal.setPaymentWay(PaymentWay.ONLINE.getComment());
                    break;
                case "OUTLINE":
                    orderReal.setPaymentWay(PaymentWay.OUTLINE.getComment());
                    break;
                    default:
                        orderReal.setPaymentWay(PaymentWay.ONLINE.getComment());
                        break;
            }
            //送货地址
            orderReal.setDeliveryAddress(orderInfo.getDeliveryAddress());
            //用户的订单备注
            if (StringUtils.isNotBlank(orderInfo.getOrderComment())) {
                orderReal.setOrderComment(orderInfo.getOrderComment());
            } else {
                orderReal.setOrderComment("用户未备注");
            }
            //订单的外部编号（全网唯一）
            //规则：公司域名+系统名称+系统时间+订单生成时间+用户id
            //生成订单生成时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String orderCreatTime = sdf.format(date.getTime());
            //生成订单的外部编号（全网唯一）
            String outTradeNo = WebConst.BUSINESS_DOMAIN + WebConst.SYSTEM_NAME + System.currentTimeMillis() + orderCreatTime + userId;
            //外部编号
            orderReal.setOutTradeNo(outTradeNo);

            orderReal.setCreateTime(date);
            Date createTime = orderReal.getCreateTime();

            //订单的过程状态
            orderReal.setProcessStatus(ProcessStatus.UNPAID.getComment());
            //创建OrderDetail对象的List，用于封装真实的订单详情数据
            List<OrderDetail> orderDetailList = new ArrayList<>();
            //创建用于封装选中的购物车id对象，用于删除的时候使用
            List<Long> checkedCartIdList = new ArrayList<>();
            //创建用于计算合计总价的对象
            BigDecimal totalAmount = new BigDecimal(0);

            //遍历强一致性的购物车数据
            for (CartInfo cartInfo : cartInfoList) {
                //只封装勾选状态下的数据
                if ("1".equals(cartInfo.getIsChecked())) {
                    //创建订单详情对象，用于封装数据
                    OrderDetail orderDetail = new OrderDetail();
                    //封装单种商品总价
                    orderDetail.setOrderPrice(cartInfo.getCartPrice());
                    //封装单种商品的数量
                    orderDetail.setSkuNum(String.valueOf(cartInfo.getSkuNum()));
                    //封装单种商品的名称
                    orderDetail.setSkuName(cartInfo.getSkuName());
                    //封装单种商品的Id
                    orderDetail.setSkuId(cartInfo.getSkuId());
                    //封装单种商品的图片地址
                    orderDetail.setImgUrl(cartInfo.getImgUrl());
                    //将多个orderDetail对象封装到list
                    orderDetailList.add(orderDetail);
                    //将每个选中状中的购物车封装到集合中
                    checkedCartIdList.add(cartInfo.getId());
                    //将每个购物车商品价格进行相加
                    totalAmount = totalAmount.add(cartInfo.getCartPrice());
                }

            }
            //封装总价
            orderReal.setTotalAmount(totalAmount);
            //封装到orderInfo
            orderReal.setOrderDetailList(orderDetailList);
            //保存订单到数据库
            Long orderId = saveOrder(orderReal);
            //保存无问题后进行删除购物车数据
            if (orderId != null) {
                //删除数据库选中的
                cartService.removeCheckedCart(userId, checkedCartIdList);
                //将orderId返回
                orderReal.setId(orderId);
            }
        }

        return orderReal;
    }

    /**
     * 保存订单到数据库
     *
     * @param orderReal 订单信息
     */
    private Long saveOrder(OrderInfo orderReal) {

        try {
            //先保存订单信息，之后会返回插入的id值
            orderInfoMapper.insertSelective(orderReal);
            Long orderId = orderReal.getId();
            List<OrderDetail> orderDetailList = orderReal.getOrderDetailList();
            //遍历封装好的orderDetailList并进行保存数据库操作
            for (OrderDetail orderDetail : orderDetailList) {
                //将orderId封装
                orderDetail.setOrderId(orderId);
                orderDetailMapper.insertSelective(orderDetail);
            }
            return orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
