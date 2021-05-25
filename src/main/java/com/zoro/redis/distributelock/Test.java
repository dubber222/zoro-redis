package com.zoro.redis.distributelock;

/**
 * Demo class
 *
 * @author dubber
 * @date 2018/10/7
 */
public class Test implements Runnable {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Test t = new Test();
            new Thread(t, "tName" + i).start();
        }
    }

    @Override
    public void run() {
        while (true) {
            DistributeLockDemo distributeLockDemo = new DistributeLockDemo();
            String identifier = distributeLockDemo.acquireLock("update", 2000, 50000);
            if (identifier != null) {
                System.out.println(Thread.currentThread().getName() + "->成功获得锁::" + identifier);
                try {
                    Thread.sleep(1000);
                    distributeLockDemo.releaseLock("update", identifier);
                    //distributeLockDemo.releaseLockWithLua("update",identifier);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
