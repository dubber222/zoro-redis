package com.zoro.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/10/10
 */
public class PipelineDemo {

    public static void main(String[] args) {

        Jedis jedis = RedisConnectionUtil.getJedis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("a","1");
        pipeline.set("b","2");
        pipeline.set("c","3");
        pipeline.set("d","4");

        pipeline.sync();
    }
}

