package com.zoro.redis;

import com.google.common.hash.BloomFilter;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/10/1
 */
public class BloomFilterTest {

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

        RBloomFilter<String> bloom = redissonClient.getBloomFilter("a");
        // 初始化布隆过滤器， 预计元素 1000000，误差0.03
        bloom.tryInit(1000000,0.03);
        bloom.add("abc");
        System.out.println(bloom.contains("abc"));
        System.out.println(bloom.contains("123"));

        redissonClient.shutdown();
    }
}
