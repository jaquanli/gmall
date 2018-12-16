package com.atguigu.gmall.payment.controller;

import com.atguigu.gmall.annotations.LoginInterceptCondition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PaymentController {

    @LoginInterceptCondition
    @RequestMapping("index")
    public String toPaymentIndex(HttpServletRequest request, ModelMap modelMap){
        //将拦截器保存到请求域中的用户信息获取到
        String userId = (String) request.getAttribute("userId");
        String nickName = (String) request.getAttribute("nickName");
        modelMap.put("nickName",nickName);
        modelMap.put("userId",userId);


        String orderId = request.getParameter("orderId");
        String totalAmount = request.getParameter("totalAmount");

        modelMap.put("orderId",orderId);
        modelMap.put("totalAmount",totalAmount);

        return "index";

    }



}
