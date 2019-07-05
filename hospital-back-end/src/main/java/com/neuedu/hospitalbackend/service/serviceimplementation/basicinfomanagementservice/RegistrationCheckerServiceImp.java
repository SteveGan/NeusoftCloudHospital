package com.neuedu.hospitalbackend.service.serviceimplementation.basicinfomanagementservice;


import com.neuedu.hospitalbackend.redis.RedisDistributeLock;
import com.neuedu.hospitalbackend.service.serviceinterface.basicinfomanagementservice.RegistrationChecker;
import io.lettuce.core.cluster.api.async.RedisClusterAsyncCommands;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

public class RegistrationCheckerServiceImp implements RegistrationChecker {

    @Resource
    private RedisDistributeLock redisDistributeLock;

    @Resource
    private JedisCluster jedisCluster;

    /**
     * Update available registrations of a doctor
     * in the Redis Cluster
     * @param appointmentDate
     * @param roleId
     * @param timeSlot
     * @return true if operate successful
     */
    @Override
    public boolean regist(String appointmentDate, String roleId, Byte timeSlot){
        redisDistributeLock.tryLock("TEST_LOCK_KEY", "TEST_LOCK_VAL_", 1000* 100, 1000*20);
        Integer leftAppointments = Integer.parseInt(jedisCluster.get(appointmentDate+":"+timeSlot+":"+roleId));
        if(leftAppointments > 0) {
            jedisCluster.set(appointmentDate + ":" + timeSlot + ":" + roleId, String.valueOf(leftAppointments - 1));
            return true;
        }else{
            return false;
        }
    }


}
