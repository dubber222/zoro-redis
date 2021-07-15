package com.zoro.redis.spinlock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis 实现自旋锁
 * 测试见 Test 单元测试
 *
 * @author chenershuai
 * @date 2021/7/6 9:31
 */

@Slf4j
@Component
public class SpinLock {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取自旋锁
     *
     * @param key
     * @param value
     * @param retryTimes 自旋次数
     * @param sleepTimes 每次自旋等待时间
     * @param timeout    持有锁时间
     * @return
     * @throws InterruptedException
     */
    public boolean getSpinLock(String key, String value,
                               int retryTimes, int sleepTimes, int timeout)
            throws InterruptedException {
        log.info(Thread.currentThread().getName() + "开始获取锁");

        for (int i = 0; i < retryTimes; i++) {
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, value);
            if (null != lock && lock) {
                log.info(Thread.currentThread().getName() + "获取锁");
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
                return true;
            }

            log.info(Thread.currentThread().getName() + "自旋等待锁");
            Thread.sleep(sleepTimes * 1000);
        }
        return false;
    }
}
