package com.atguigu.gmall.manager;

import com.atguigu.gmall.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManagerServiceApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Value("${spring.redis.password}")
    String redisPassword;
    @Test
    public void contextLoads() {

        Jedis jedis = redisUtil.getJedis();
        //jedis.auth(redisPassword);

        String ping = jedis.ping();
        System.err.println(ping);
        jedis.close();
    }

}
