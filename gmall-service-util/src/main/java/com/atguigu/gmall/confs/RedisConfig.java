package com.atguigu.gmall.confs;

import com.atguigu.gmall.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host:disabled}")
    private String redisHost;

    @Value("${spring.redis.port:0}")
    private Integer redisPort;

    @Value("${spring.redis.database:0}")
    private Integer redisDatabase;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public RedisUtil getRedisUtil(){

        if (redisHost.equals("disabled")){
            return null;
        }

        RedisUtil redisUtil = new RedisUtil();
        redisUtil.initPool(redisHost,redisPort,redisDatabase,redisPassword);

        return redisUtil;
    }



}
