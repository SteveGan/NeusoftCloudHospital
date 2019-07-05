package com.neuedu.hospitalbackend.redis;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class JedisClusterConfig {

    @Value("${spring.redis.clusterNodes}")
    private String clusterNodes;

    @Value("${spring.redis.commandTimeout}")
    private Integer commandTimeout;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.minIdle}")
    private Integer minIdle;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${spring.redis.soTimeout}")
    private Integer soTimeout;


//    @Value("${spring.redis.maxAttempts}")
//    private Integer maxAttempts;



    @Bean
    public JedisCluster getJedisCluster() {
        System.out.println("---------------------------------------");
        System.out.println(clusterNodes);
        System.out.println(password);
        System.out.println("---------------------------------------");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);

        String[] serverArray = clusterNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            System.out.println("pair:" + ipPortPair[0] + ":" + ipPortPair[1]);
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        JedisCluster cluster =  new JedisCluster(nodes);
        System.out.println("Cluster Size...." + cluster.getClusterNodes().size());
        return cluster;
    }

}
