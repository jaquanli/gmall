package com.atguigu.gmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.annotations.LoginInterceptCondition;
import com.atguigu.gmall.bean.OrderDetail;
import com.atguigu.gmall.bean.OrderInfo;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {

    @Reference
    private UserService userService;

    @Reference
    private OrderService orderService;

    /**
     *
     * @param orderInfo 页面请求的数据，部分可以使用
     * @return 页面
     */
    @LoginInterceptCondition
    @RequestMapping("submitOrder")
    public ModelAndView submitOrder(HttpServletRequest request, OrderInfo orderInfo){

        ModelAndView modelAndView = new ModelAndView();
        //将拦截器保存到请求域中的用户信息获取到
        String userId = (String) request.getAttribute("userId");
        String nickName = (String) request.getAttribute("nickName");
        //获取页面请求过来的交易码
        String tradeCode = request.getParameter("tradeCode");
        //首先根据tradeCode去redis查是否存在，存在就删除对于的交易码，然后继续，不存在就返回失败页面
        Boolean isTradeCodeFlag =  orderService.isTradeCode(userId,tradeCode);
        //当为true时，就创建订单，否就返回失败页面
        if (isTradeCodeFlag){
            //在这里完成订单的生成，并处理购物车中的数据进行删除操作
            OrderInfo orderReal =  orderService.creatOrder(userId,orderInfo);
            if (orderReal != null) {
                //跳转支付页面
                modelAndView.setViewName("redirect:http://192.168.2.31:8087/index");
                //将需要的参数带回
                modelAndView.addObject("outTradeNo",orderReal.getOutTradeNo());
                modelAndView.addObject("totalAmount",orderReal.getTotalAmount());
            }else {
                modelAndView.setViewName("tradeFail");
            }
        }else {
            modelAndView.setViewName("tradeFail");
        }
        return modelAndView;
    }

    /**
     * 当用户点击到结算时，将需要的用户核对的订单信息展示在页面，
     * 但最终作为订单信息的实际数据不是页面数据，而是购物车数据库
     * 中勾选的数据
     * @return 返回页面
     */
    @LoginInterceptCondition
    @RequestMapping("toTrade")
    public String toTrade(HttpServletRequest request, ModelMap modelMap) {
        //获取在拦截器放到请求域中的用户信息
        String userId = (String) request.getAttribute("userId");
        String nickName = (String) request.getAttribute("nickName");
        //将用户信息保存到页面
        modelMap.put("userId", userId);
        modelMap.put("nickName", nickName);
        //获取用户的收货地址信息
        List<UserAddress> userAddressList = userService.queryUserAddressListByUserId(userId);
        modelMap.put("userAddressList", userAddressList);
        //创建用于给用户展示的订单信息，这里并不作为最终订单提交信息的提供，只是作为用户核对作用
        OrderInfo orderInfo = orderService.creatCheckOrderInfo(userId);
        //将订单信息取出，展示在页面
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        modelMap.put("orderDetailList", orderDetailList);
        //将计算的总价取出，展示在页面
        BigDecimal totalAmount = orderInfo.getTotalAmount();
        modelMap.put("totalAmount",totalAmount);
        //生成交易码，防止多次提交订单情况
        String tradeCode = UUID.randomUUID().toString();
        modelMap.put("tradeCode",tradeCode);
        //将生产的交易码暂时保存到redis，在提交订单时进行校验
        orderService.addTempTradeCode(userId,tradeCode);
        return "trade";
    }
}
