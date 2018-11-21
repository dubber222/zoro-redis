package com.zoro.redis.distributelock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/10/9
 */
public class RedissonDistributeLock {

    public static void main(String[] args) {

        Config config = new Config();
        config.useSingleServer().setTimeout(1000000)
                .setAddress("redis://192.168.116.12:6379");
        RedissonClient redissonClient = Redisson.create(config);

        RLock rLock = redissonClient.getLock("update");
        try {
            rLock.tryLock(100,10, TimeUnit.SECONDS);
            Thread.sleep(100);
            System.out.println("获得锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            redissonClient.shutdown();
        }
    }
}
