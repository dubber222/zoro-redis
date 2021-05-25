package com.zoro.redis.sentinel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenershuai
 * @date 2021/5/25 15:53
 */
public class RedisSentinelDemo {

    public static void main(String[] args) {
        //连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);

        //哨兵信息
        Set<String> sentinels = new HashSet<String>(Arrays.asList(
                "192.168.137.219:26379",
                "192.168.137.219:26380",
                "192.168.137.219:26381"
        ));

        //创建连接池
        //mymaster是我们配置给哨兵的服务名称
        //sentinels是哨兵信息
        //jedisPoolConfig是连接池配置
        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig);

        //获取客户端连接
        Jedis jedis = pool.getResource();
        //设置k1
        jedis.set("k1", "v1");
        String myvalue = jedis.get("k1");
        //获取k1的值
        System.out.println(myvalue);
    }
}
