package redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Set<HostAndPort> clusterNodes = new HashSet<>();
        clusterNodes.add(new HostAndPort("localhost", 6390));
        clusterNodes.add(new HostAndPort("localhost", 6391));
        clusterNodes.add(new HostAndPort("localhost", 6392));
//        clusterNodes.add(new HostAndPort("172.16.8.70", 6391));
//        clusterNodes.add(new HostAndPort("172.16.8.70", 6392));
//
        JedisCluster jedisCluster = new JedisCluster(clusterNodes);
        jedisCluster.set("key", "zhangsan");
        System.out.println(jedisCluster.get("key"));

//        Jedis jedis = new Jedis("172.16.8.70", 6390);
//        jedis.set("key", "zhangsan");
//        System.out.println(jedis.get("key"));
    }

}
