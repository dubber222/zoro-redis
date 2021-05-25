package com.zoro.redis.distributelock;

import com.zoro.redis.RedisConnectionUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.UUID;

/**
 * 分布式锁：获得锁、释放锁;
 * 获得锁: 唯一id（标识获得锁的进程或者线程id），获得锁的超时时间+锁的超时时间。
 *
 * @author dubber
 * @date 2018/10/6
 */
public class DistributeLockDemo {

    private String prefix = "distriBute_";

    /**
     * 获得锁  setnx
     *
     * @param lockName       锁名称
     * @param acquireTimeOut 获取锁超时时间
     * @param lockTimeOut    锁的超时时间
     * @return
     */
    public String acquireLock(String lockName, long acquireTimeOut, long lockTimeOut) {
        String identifier = UUID.randomUUID().toString();
        lockName = prefix + lockName;
        Jedis jedis = null;
        try {
            jedis = RedisConnectionUtil.getJedis();
            //重试机制
            long end = System.currentTimeMillis() + acquireTimeOut;
            while (end > System.currentTimeMillis()) {
                /*long value = jedis.setnx(lockName, identifier);
                if (value == 1) {
                    //设置超时时间
                    jedis.expire(lockName, (int) (lockTimeOut / 1000));
                    return identifier;
                }

                if (jedis.ttl(lockName) == -1) {
                    jedis.expire(lockName, (int) (lockTimeOut / 1000));
                }*/

                // setnx 修改为支持“原子性”的 setEX
                String result = jedis.setex(lockName, lockTimeOut / 1000, identifier);
                if (StringUtils.isNotBlank(result) && "OK".equalsIgnoreCase(result)) {
                    return identifier;
                }
                if (jedis.ttl(lockName) == -1) {
                    jedis.setex(lockName, lockTimeOut / 1000, identifier);
                }

                try {
                    //等待片刻后，重试获取锁
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            // 回收redis
            jedis.close();
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param lockName
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {
        boolean isRelease = false;
        lockName = prefix + lockName;
        // try-with-resource
        try (Jedis jedis = RedisConnectionUtil.getJedis();) {
            while (true) {
                // 如果有其他事务操作了 lockName，下面的事务不生效
                jedis.watch(lockName);
                //判断是否是同一把锁
                if (identifier.equals(jedis.get(lockName))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockName);
                    if (transaction.exec().isEmpty()) {
                        continue;
                    }
                    System.out.println(Thread.currentThread().getName() + "-> 成功释放锁:" + identifier);
                    isRelease = true;
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRelease;
    }

    /**
     * 基于 lua 基本释放锁
     *
     * @param lockName
     * @param identifier
     * @return
     */
    public boolean releaseLockWithLua(String lockName, String identifier) {
        Jedis jedis = RedisConnectionUtil.getJedis();
        String lockKey = prefix + lockName;

        String lua = "if redis.call(\"get\",KEYS[1])==ARGV[1] then " +
                "return redis.call(\"del\",KEYS[1]) " +
                "else return 0 end";

        Long rs = (Long) jedis.eval(lua, 1, new String[]{lockKey, identifier});
        if (rs.intValue() > 0) {
            System.out.println(Thread.currentThread().getName() + "-> 成功释放锁:" + identifier);
            return true;
        }
        return false;

    }

}
