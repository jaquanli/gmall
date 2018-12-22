package com.atguigu.gmall.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.gmall.annotations.LoginInterceptCondition;
import com.atguigu.gmall.bean.OrderDetail;
import com.atguigu.gmall.bean.OrderInfo;
import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.bean.enums.PaymentStatus;
import com.atguigu.gmall.confs.AlipayConfig;
import com.atguigu.gmall.consts.MessageConst;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.PaymentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
public class PaymentController {


    @Reference
    private OrderService orderService;

    @Reference
    private PaymentService paymentService;

    final
    AlipayClient alipayClient;

    @Autowired
    public PaymentController(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }


    @LoginInterceptCondition
    @RequestMapping("index")
    public String toPaymentIndex(HttpServletRequest request, ModelMap modelMap) {
        //将拦截器保存到请求域中的用户信息获取到
        String userId = (String) request.getAttribute("userId");
        String nickName = (String) request.getAttribute("nickName");
        modelMap.put("nickName", nickName);
        modelMap.put("userId", userId);

        String outTradeNo = request.getParameter("outTradeNo");
        String totalAmount = request.getParameter("totalAmount");


        modelMap.put("outTradeNo", outTradeNo);
        modelMap.put("totalAmount", totalAmount);

        return "index";
    }

    @LoginInterceptCondition
    @RequestMapping("aliPay/submit")
    @ResponseBody
    public String aliPaySubmit(HttpServletRequest request) {
        //将拦截器保存到请求域中的用户信息获取到
        String userId = (String) request.getAttribute("userId");
        String nickName = (String) request.getAttribute("nickName");

        //获取from表单传来的参数
        String outTradeNo = (String) request.getAttribute("outTradeNo");
        if (StringUtils.isBlank(outTradeNo)) {
            outTradeNo = request.getParameter("outTradeNo");
        }
        String totalAmount = (String) request.getAttribute("totalAmount");
        if (StringUtils.isBlank(totalAmount)) {
            totalAmount = request.getParameter("totalAmount");
        }

        //设置测试价格
        totalAmount = "0.01";

        //创建AliPay的发送支付请求的对象
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        //设置AliPay的同步回调地址，对应Gmall项目的同步请求方法
        aliPayRequest.setReturnUrl(AlipayConfig.return_payment_url);
        //设置AliPay的异步回调地址，对应Gmall项目在公网并已经备案的域名地址中对应异步请求方法
        aliPayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
        //设置AliPay需要的业务参数
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //Map<String, String> aliPayReturnParam = new HashMap<>();

        //塞入Gmall商城的订单全网编号
        model.setOutTradeNo(outTradeNo);
        //aliPayReturnParam.put("out_trade_no",outTradeNo);

        //塞入aliPay的产品编号
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        //aliPayReturnParam.put("product_code","FAST_INSTANT_TRADE_PAY");

        //塞入总价
        model.setTotalAmount(totalAmount);
        //aliPayReturnParam.put("total_amount",totalAmount);

        //查询订单详情
        OrderInfo orderInfo = orderService.getOrderByOutTradeNo(outTradeNo);
        //获取商品列表的第一个，塞入标题orderInfo.getOrderDetailList().get(0).getSkuName()
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        String skuSubject = orderDetailList.get(0).getSkuName();
        //计算几件商品
        Integer totalNum = 0;
        for (OrderDetail orderDetail : orderDetailList) {
            totalNum += Integer.parseInt(orderDetail.getSkuNum());
        }
        //标题
        model.setSubject(skuSubject + "等" + totalNum + "件商品。");
        //aliPayReturnParam.put("subject",orderInfo.getOrderDetailList().get(0).getSkuName());
        //将业务参数传入
        aliPayRequest.setBizModel(model);


        String responseBody = "";
        try {

            //通过aliPay客户端调用，返回一个响应体，直接输出页面即可
            responseBody = alipayClient.pageExecute(aliPayRequest).getBody();

            //获取支付信息
            PaymentInfo paymentInfoDB = paymentService.getPaymentInfoByOutTradeNo(outTradeNo);
            //幂等性检查，只有支付信息不存在的时候创建支付信息
            if (paymentInfoDB == null) {

                //应该在这里创建支付信息，在支付宝的回调中更新支付信息
                PaymentInfo paymentInfo = new PaymentInfo();
                //AliPay交易号,为空就行，等到交易成功再更新
                // paymentInfo.setAlipayTradeNo(trade_no);
                //外部订单号
                paymentInfo.setOutTradeNo(outTradeNo);
                //总价
                paymentInfo.setTotalAmount(new BigDecimal(totalAmount));
                //订单标题
                paymentInfo.setSubject(skuSubject);
                //订单id
                paymentInfo.setOrderId(String.valueOf(orderInfo.getId()));
                //支付宝回调的时间,为空，产生回调时更新
                //paymentInfo.setCallbackTime(new Date());
                //支付信息支付状态
                paymentInfo.setPaymentStatus(PaymentStatus.UNPAID.getName());
                //支付信息创建时间
                paymentInfo.setCreateTime(new Date());
                //创建
                paymentService.addPaymentInfo(paymentInfo);
            }

            //在这里要发送延迟队列的定时任务
            int delaySec = 40;//每次检查延迟的秒数
            int checkCount = 3;//检查次数
            paymentService.sendDelayPaymentResultMessage(outTradeNo, delaySec, checkCount);

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    @LoginInterceptCondition
    @RequestMapping("alipay/callback/return")
    public String aliPayCallback(HttpServletRequest request) {

        //支付完成后还要完成一系列操作，比如订单状态的修改，库存系统收到消息，支付状态的修改等等。。。
        //保存支付信息
        String out_trade_no = request.getParameter("out_trade_no");
        String total_amount = request.getParameter("total_amount");
        String trade_no = request.getParameter("trade_no");
        String sign = request.getParameter("sign");
        if (StringUtils.isNotBlank(out_trade_no)) {

            //获取支付信息
            PaymentInfo paymentInfoDB = paymentService.getPaymentInfoByOutTradeNo(out_trade_no);
            //幂等性检查，并更新
            if (paymentInfoDB != null) {
                paymentInfoDB.setAlipayTradeNo(trade_no);
                paymentInfoDB.setCallbackTime(new Date());
                paymentInfoDB.setCallbackContent(request.getQueryString());
                //检查并更新
                PaymentInfo paymentInfo = paymentService.checkPaymentState(paymentInfoDB);
                //这里应该校验支付宝是否收到到账消息，如果收到到账消息，进行更新，如果没有，则进行重新发送请求
                if (PaymentStatus.PAID.getName().equals(paymentInfo.getPaymentStatus())){
                    //发送订单支付结果的消息,并通过orderService接收消息，处理订单
                    String paymentResult = MessageConst.MESSAGE_SUCCESS;
                    paymentService.sendPaymentResultMessage(paymentInfo, paymentResult);
                }
            }
        } else {
            return "filed";
        }
        return "finish";
    }


}
