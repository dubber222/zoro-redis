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
        config.useClusterServers().addNodeAddress("redis://192.168.116.12:7000", "redis://192.168.116.12:7001");
        RedissonClient redissonClient = Redisson.create(config);
        // redissonClient.getLock();
        redissonClient.getBucket("zoro").set("ana");

        System.out.println(redissonClient.getBucket("zoro").get());

        redissonClient.shutdown();
    }
}
