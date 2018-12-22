package com.atguigu.gmall.consts;

public interface WebConst {
    //单点登录页面地址
    String LOGIN_URL = "http://192.168.2.31:8085/index";
    //单点登录验证服务
    String VERIFY_URL = "http://192.168.2.31:8085/verify";
    //JWT加密解密企业秘钥
    String BUSINESS_KEY = "AT_GUI_GU";
    //redis存储user信息的超时时间7天
    int USER_INFO_OUT_TIME = 60 * 60 * 24 * 7;
    //存储用户信息token的cookie的name
    String COOKIE_USER_TOKEN = "USER_TOKEN";

    //购物车cookie姓名
    String CART_COOKIE_NAME = "CART_COOKIE";
    //最大cookie过期时间
    int COOKIE_OVER_TIME_MAX = 60 * 60 * 24 * 7;//7天
    //最小cookie过期时间
    int COOKIE_OVER_TIME_MIN = 60 * 60 * 24;//1天
    //订单模块中交易码在redis的过期时间，秒级
    int TRADE_CODE_OUT_TIME = 60 * 30;//30分钟
    //企业域名
    String BUSINESS_DOMAIN = "ATGUIGU";
    //系统名称
    String SYSTEM_NAME = "GMALL";
}
