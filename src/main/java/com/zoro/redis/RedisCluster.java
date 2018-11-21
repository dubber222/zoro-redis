package com.zoro.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2018/9/30.
 *
 * @author dubber
 */
public class RedisCluster {


    public static void main(String[] args) {
        Set<HostAndPort> nodes = new HashSet<>();
        HostAndPort hap0 = new HostAndPort("192.168.116.12",7000);
        HostAndPort hap1 = new HostAndPort("192.168.116.12",7001);
        HostAndPort hap2 = new HostAndPort("192.168.116.12",7002);
        HostAndPort hap3 = new HostAndPort("192.168.116.12",7003);
        HostAndPort hap4 = new HostAndPort("192.168.116.12",7004);

        nodes.add(hap0);
        nodes.add(hap1);
        nodes.add(hap2);
        nodes.add(hap3);
        nodes.add(hap4);

        JedisCluster jedisCluster = new JedisCluster(nodes);

        jedisCluster.set("anan","beautifull!!");

        //  sentinel 集群
        //JedisSentinelPool jedisSentinelPool = new JedisSentinelPool()
    }

}
