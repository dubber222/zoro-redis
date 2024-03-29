package com.zoro.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/10/1
 */
public class RedissonCluster {

    public static void main(String[] args) {

        Config config = new Config();
        config.useClusterServers().setPassword("1234")
                .addNodeAddress("redis://192.168.113.62:6371",
                        "redis://192.168.113.62:6372",
                        "redis://192.168.113.62:6373",
                        "redis://192.168.113.62:6374",
                        "redis://192.168.113.62:6375",
                        "redis://192.168.113.62:6376"
                        );
        RedissonClient redissonClient = Redisson.create(config);


        redissonClient.getBucket("zoro").set("ana");


        System.out.println(redissonClient.getBucket("zoro").get());

        redissonClient.shutdown();
    }
}
