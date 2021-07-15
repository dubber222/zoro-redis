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
        new PipelineDemo().normal(jedis);
        //new PipelineDemo().pipeline(jedis);
    }

    public void normal(Jedis jedis){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            jedis.set("333" +i, String.valueOf(i));
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    public void pipeline(Jedis jedis){
        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        pipeline.multi();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("333" +i, String.valueOf(i));
        }
        pipeline.exec();
        System.out.println(System.currentTimeMillis() - start);
    }



}

