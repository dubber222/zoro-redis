package com.zoro;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试redis cluster
 *
 * @author chenershuai
 * @date 2021/7/6 9:49
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisClusterTest {

    @Autowired
    private RedisTemplate redisTemplate;

    private final String _last = "_last";

    @Test
    public void test() {
        ClusterOperations cluster = redisTemplate.opsForCluster();

    }

    @Test
    public void count() throws InterruptedException {
        ValueOperations<String, Long> vOpre = redisTemplate.opsForValue();
        long last = 1;
        while (last > 0) {
            try {
                last = Long.parseLong(String.valueOf(vOpre.get(_last)));
            } catch (Exception e) {
                log.error(e.getMessage());
                Thread.currentThread().sleep(1 * 1000);
            }
        }
    }

    @Test
    public void nonStop() throws InterruptedException {
        ValueOperations<String, Integer> vOpre = redisTemplate.opsForValue();
        Integer last = vOpre.get(_last);
        if (null == last)
            last = 1;
        for (int i = last; i < 1000000000; i++) {
            try {
                vOpre.set("foo" + i, i);
                log.info(String.valueOf(vOpre.get("foo" + i)));
                vOpre.set(_last, i);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            Thread.currentThread().sleep((5 * 1000));
        }
    }


    @Test
    public void clusterOpre(){
        ClusterOperations clusterOperations = redisTemplate.opsForCluster();


    }


}
