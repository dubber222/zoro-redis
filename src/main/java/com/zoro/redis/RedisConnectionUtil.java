package com.zoro.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/10/6
 */
public class RedisConnectionUtil {

    private static JedisPool jedisPool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        jedisPool = new JedisPool(config, "192.168.113.62", 6379);
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void main(String[] args) {
        Jedis jedis = RedisConnectionUtil.getJedis();
        jedis.set("test","test2");
    }
}
