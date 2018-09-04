package com.taotao.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 李壮壮 on 2018/9/1.
 */
public class TestJedis {

    @Test
    public void tetsJedis(){
        Jedis jedis = new Jedis("192.168.25.129", 6379);
        jedis.set("test-key","123");
        System.out.println(jedis.get("test-key"));
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception {
        //创建一个数据库连接池对象（单例），需要指定服务的ip和端口号
        JedisPool jedisPool = new JedisPool("192.168.25.129", 6379);
        //从连接池中获得连接
        Jedis jedis = jedisPool.getResource();
        //使用Jedis操作数据库（方法级别使用）
        String result = jedis.get("jedis-key");
        System.out.println(result);
        //一定要关闭Jedis连接
        jedis.close();
        //系统关闭前关闭连接池
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception {
        //创建一个JedisCluster对象，构造参数Set类型，集合中每个元素是HostAndPort类型
        Set<HostAndPort> nodes = new HashSet<>();
        //向集合中添加节点
        nodes.add(new HostAndPort("192.168.25.129", 7001));
        nodes.add(new HostAndPort("192.168.25.129", 7002));
        nodes.add(new HostAndPort("192.168.25.129", 7003));
        nodes.add(new HostAndPort("192.168.25.129", 7004));
        nodes.add(new HostAndPort("192.168.25.129", 7005));
        nodes.add(new HostAndPort("192.168.25.129", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //直接使用JedisCluster操作redis，自带连接池。jedisCluster对象可以是单例 的。
        jedisCluster.set("cluster-test", "hello jedis cluster");
        String string = jedisCluster.get("cluster-test");
        System.out.println(string);
        //系统关闭前关闭JedisCluster
        jedisCluster.close();
    }
}
