package com.atguigu.gmall.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.atguigu.gmall.payment.mapper")
@ComponentScan("com.atguigu.gmall")
public class GmallPaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentServiceApplication.class, args);
    }

}

