package com.zoro;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * 测试redis pipeline
 *
 * @author chenershuai
 * @date 2021/7/6 9:49
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PipelineTest {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void test() {
        // normal();
         pipeline();
    }

    public void normal(){
        ValueOperations<String,String> oper =  redisTemplate.opsForValue();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            oper.set("333" +i, String.valueOf(i));
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    public void pipeline(){
        long start = System.currentTimeMillis();
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (int i = 0; i < 10000; i++) {
                    String key = "123" + i;
                    connection.zCount(key.getBytes(), 0,Integer.MAX_VALUE);
                }
                List<Object> result=connection.closePipeline();
                return null;
            }
        });
        System.out.println(System.currentTimeMillis() - start);
    }

}
