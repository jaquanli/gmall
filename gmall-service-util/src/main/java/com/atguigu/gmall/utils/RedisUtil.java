package com.atguigu.gmall.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    private JedisPool jedisPool;

    public void initPool(String redisHost,int redisPort,int redisDatabase,String password){
        //使用jedis配置为jedis连接池设置配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxIdle(30);//设置最大空闲连接数
        jedisPoolConfig.setMaxTotal(200);//设置最大连接数
        jedisPoolConfig.setMaxWaitMillis(100 * 1000);//设置最大等待时间
        //jedisPoolConfig.setBlockWhenExhausted(true);//设置当连接耗尽时是否阻塞，阻塞等待到超时，默认为true，可以不用设置
        jedisPoolConfig.setTestOnBorrow(true);// 获得一个jedis连接时检测可用性,默认为false
        //通过构造器获得jedis连接池
        jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, 200 * 1000,password);

    }

    public Jedis getJedis(){
        //从jedis连接池中获取jedis连接资源
        return jedisPool.getResource();
    }
}
