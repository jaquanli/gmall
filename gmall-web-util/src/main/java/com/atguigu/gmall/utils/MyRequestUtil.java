package com.atguigu.gmall.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class MyRequestUtil {


    /**
     * 根据请求获取用户的IP地址
     * @param request 请求
     * @return IP地址
     */
    public static String getRemoteRequestIPAddr(HttpServletRequest request) {

        //获取用户客户端请求IP地址
        String IPAddr = request.getRemoteAddr();
        if (StringUtils.isBlank(IPAddr)){
            IPAddr = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(IPAddr)){
                IPAddr = "127.0.0.1";
            }
        }
        return IPAddr;
    }

}
