package com.atguigu.gmall.passport.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.ResultEntity;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.consts.WebConst;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.PassportService;
import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.utils.CookieUtil;
import com.atguigu.gmall.utils.JwtUtil;
import com.atguigu.gmall.utils.MyRequestUtil;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PassportController {

    @Reference
    private UserService userService;

    @Reference
    private CartService cartService;

    @Reference
    private PassportService passportService;

    @RequestMapping("index")
    public String doLogin(@RequestParam("originUrl") String originUrl, ModelMap modelMap) {
        modelMap.put("originUrl", originUrl);

        return "index";

    }

    @ResponseBody
    @RequestMapping("login")
    public ResultEntity<String> login(HttpServletRequest request, HttpServletResponse response) {

        ResultEntity<String> resultEntity = new ResultEntity<>();
        //获取请求中的信息
        String loginName = request.getParameter("loginName");
        String passwd = request.getParameter("passwd");
        //String originUrl = request.getParameter("originUrl");

        //取DB中的数据
        UserInfo userInfo = userService.queryUserInfoByLoginNameAndPasswd(loginName, passwd);

        if (userInfo != null) {
            //创建map用于封装token参数
            Map<String, String> map = new HashMap<>();
            map.put("userId", String.valueOf(userInfo.getId()));
            map.put("nickName", userInfo.getNickName());
            //获取用户请求的IP作为盐值
            String userIP = MyRequestUtil.getRemoteRequestIPAddr(request);
            //加密用户的信息
            String encodeToken = JwtUtil.encode(WebConst.BUSINESS_KEY, map, userIP);
            //返回成功的消息
            resultEntity.setResult(ResultEntity.SUCCESS);
            resultEntity.setDate(encodeToken);

            /*
             * 购物车合并
             * 这里暂时采取折中的办法，以后使用消息队列MQ彻底解决购物车合并问题
             */


            //这里是登录成功，取出购物车的cookie信息
            String cartCookieValue = CookieUtil.getCookieValue(request, WebConst.CART_COOKIE_NAME, true);
            if (StringUtils.isNotBlank(cartCookieValue)){
                Long userId = userInfo.getId();
                //发送合并购物车的消息
                passportService.sendMergeCartMessage(userId,cartCookieValue);

                //删除cookie中的数据
                CookieUtil.deleteCookie(request,response,WebConst.CART_COOKIE_NAME);

                //Boolean mergeFlag = cartService.mergeCart(userInfo.getId(), JSON.parseArray(cartCookieValue, CartInfo.class));
                //if (mergeFlag){
                    //实现合并成功cookie后删除cookie中的数据
                   // CookieUtil.deleteCookie(request,response,WebConst.CART_COOKIE_NAME);
               // }
            }else {
                //刷新缓存的发送消息
                passportService.sendFlushCacheFormCartInfoMessage(userInfo.getId());
                //cartService.flushCacheFormCartInfo(userInfo.getId());
            }

        } else {
            //返回失败的消息
            resultEntity.setResult(ResultEntity.FAILED);
            resultEntity.setMassage("登录账户或密码错误！");
        }
        return resultEntity;
    }

    @ResponseBody
    @RequestMapping("verify")
    public Boolean verify(HttpServletRequest request) {

        String token = request.getParameter("token");
        //这个是在发送远程调用时将参数传递过来的
        String userIp = request.getParameter("currentIP");

        try {
            Map decodeToken = JwtUtil.decode(WebConst.BUSINESS_KEY, token, userIp);
            if (decodeToken == null) {
                return false;
            }else {
                return true;
            }
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        }

    }
}
