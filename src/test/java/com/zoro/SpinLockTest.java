package com.zoro;

import com.zoro.redis.spinlock.SpinLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试redis实现自旋锁
 *
 * @author chenershuai
 * @date 2021/7/6 9:49
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpinLockTest {

    @Autowired
    private SpinLock lock;

    @Test
    public void testSpinLock() throws InterruptedException {
        Thread t1 = new Thread(new task());
        Thread t2 = new Thread(new task());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }




    class task implements Runnable{

        @Override
        public void run() {
            try {
                lock.getSpinLock("spinlock","spinlock_lock",4,1,60);

            } catch (InterruptedException e) {
                log.error("run getSpinLock fail!，message:{}",e.getMessage());
            }
        }
    }

}
