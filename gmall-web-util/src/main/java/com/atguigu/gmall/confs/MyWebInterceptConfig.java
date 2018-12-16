package com.atguigu.gmall.confs;

import com.atguigu.gmall.interceptors.LoginIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 加入到springIOC容器中
 */
@SpringBootConfiguration
public class MyWebInterceptConfig extends WebMvcConfigurerAdapter {

    private final LoginIntercept loginIntercept;

    @Autowired
    public MyWebInterceptConfig(LoginIntercept loginIntercept) {
        this.loginIntercept = loginIntercept;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginIntercept).addPathPatterns("/**");
    }
}
