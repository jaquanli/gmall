package com.atguigu.gmall.interceptors;

import com.atguigu.gmall.annotations.LoginInterceptCondition;
import com.atguigu.gmall.consts.WebConst;
import com.atguigu.gmall.utils.CookieUtil;
import com.atguigu.gmall.utils.HttpClientUtil;
import com.atguigu.gmall.utils.JwtUtil;
import com.atguigu.gmall.utils.MyRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 配置一个拦截器
 */
@Component
public class LoginIntercept extends HandlerInterceptorAdapter {

    //这个拦截的是根据配置中拦截的地址范围，拦截在范围内的所有的controller方法之前执行，为false为不放行，为true为放行，为拦截方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginInterceptCondition methodAnnotation = handlerMethod.getMethodAnnotation(LoginInterceptCondition.class);
        //当方法添加的注解不为空时，进行处理拦截
        if (methodAnnotation != null) {
            //这里有值说明是第一次登陆系统
            String newUserToken = request.getParameter("token");
            //这里有值说明是以前登陆过系统
            String userToken = CookieUtil.getCookieValue(request, WebConst.COOKIE_USER_TOKEN, true);
            //这里是要验证token是否为空
            if (StringUtils.isNotBlank(newUserToken)){
                userToken = newUserToken;
                //CookieUtil.setCookie(request,response,WebConst.COOKIE_USER_TOKEN, userToken,WebConst.COOKIE_OVER_TIME_MAX,true);
            }
            //验证token的真伪
            if (StringUtils.isNotBlank(userToken)){

                //获取用户客户端请求IP地址
                String userIP = MyRequestUtil.getRemoteRequestIPAddr(request);
                //调用远程验证中心进行验证
                String url = WebConst.VERIFY_URL + "?token=" + userToken + "&currentIP=" +userIP;
                //进行远程调用
                String s = HttpClientUtil.doGet(url);
                Boolean verifyResult = Boolean.valueOf(s);

                if (verifyResult){
                    //验证成功后将成功的token重新设置，即刷新超时时间
                    CookieUtil.setCookie(request,response,WebConst.COOKIE_USER_TOKEN, userToken,WebConst.COOKIE_OVER_TIME_MAX,true);
                    //解密出token并将用户信息放入请求中
                    Map decodeToken = JwtUtil.decode(WebConst.BUSINESS_KEY, userToken, userIP);
                    request.setAttribute("userId",decodeToken.get("userId"));
                    String nickName = (String) decodeToken.get("nickName");
                    request.setAttribute("nickName",nickName);
                    //放行
                    return true;
                }
            }

            boolean redirect = methodAnnotation.isRedirect();
            //当方法中添加的注解并含有方法默认值为true时，进行拦截
            if (redirect) {
                //获取请求时的URL，并加密返回
                String encodeRequestURL = URLEncoder.encode(request.getRequestURL().toString(), "utf-8");
                response.sendRedirect(WebConst.LOGIN_URL + "?originUrl=" + encodeRequestURL);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }



//    //这个是在preHandle方法执行完，并且放行才会执行，在页面渲染之前执行，用于处理modelAndView
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//    //这个是在postHandler方法执行完，在页面渲染之后执行，用于清理资源
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
}
