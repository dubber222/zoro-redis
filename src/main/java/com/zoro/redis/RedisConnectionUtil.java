package com.zoro.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
        jedisPool = new JedisPool(config, "192.168.116.12", 6379);
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
