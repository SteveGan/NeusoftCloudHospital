package com.neuedu.hospitalbackend.redis;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * JedisCluster + lua脚本实现分布式锁
 * @author Steve
 **/
public class RedisDistributeLock {

    private Logger logger;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * lua脚本：判断锁住值是否为当前线程持有，是的话解锁，不是的话解锁失败
     */
    private static final String DISTRIBUTE_LOCK_SCRIPT_UNLOCK_VAL = "if" +
            " redis.call('get', KEYS[1]) == ARGV[1]" +
            " then" +
            " return redis.call('del', KEYS[1])" +
            " else" +
            " return 0" +
            " end";

    private volatile String unlockSha1 = "";

    private static final Long UNLOCK_SUCCESS_CODE = 1L;

    private static final String LOCK_SUCCESS_CODE = "ok";

    public RedisDistributeLock(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }


    /**
     * 根据loopTryTime循环重试
     * @param lockKey 锁key
     * @param lockVal 锁值，用于解锁校验
     * @param expiryTime 锁过期时间
     * @param loopTryTime 获取失败时，循环重试获取锁的时长
     * @return 是否获得锁
     */
    public boolean tryLock(String lockKey, String lockVal, long expiryTime, long loopTryTime){
        Long endTime = System.currentTimeMillis() + loopTryTime;
        while (System.currentTimeMillis() < endTime){
            if (tryLock(lockKey, lockVal, expiryTime)){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据loopTryTime循环重试
     * @param lockKey 锁key
     * @param lockVal 锁值，用于解锁校验
     * @param expiryTime 锁过期时间
     * @param retryTimes 重试次数
     * @param setpTime 每次重试间隔 mills
     * @return 是否获得锁
     */
    public boolean tryLock(String lockKey, String lockVal, long expiryTime, int retryTimes, long setpTime){
        while (retryTimes > 0){
            if (tryLock(lockKey, lockVal, expiryTime)){
                return true;
            }
            retryTimes--;
            try {
                Thread.sleep(setpTime);
            } catch (InterruptedException e) {
                System.out.println("error");
            }
        }
        return false;
    }

    /**
     * 一次尝试，快速失败。不支持重入
     * @param lockKey 锁key
     * @param lockVal 锁值，用于解锁校验
     * @param expiryTime 锁过期时间 MILLS
     * @return 是否获得锁
     */
    public boolean tryLock(String lockKey, String lockVal, long expiryTime){
        //相比一般的分布式锁，这里把setNx和setExpiry操作合并到一起，jedis保证原子性，避免连个命令之间出现宕机等问题
        //这里也可以我们使用lua脚本实现
        String result = jedisCluster.set(lockKey, lockVal);
        return LOCK_SUCCESS_CODE.equalsIgnoreCase(result);
    }

    /**
     * 释放分布式锁，释放失败最可能是业务执行时间长于lockKey过期时间，应当结合业务场景调整过期时间
     * @param lockKey 锁key
     * @param lockVal 锁值
     * @return 是否释放成功
     */
    public boolean tryUnLock(String lockKey, String lockVal){
        List<String> keys = new ArrayList<>();
        keys.add(lockKey);
        List<String> argv = new ArrayList<>();
        argv.add(lockVal);
        try {
            Object result = jedisCluster.evalsha(unlockSha1, keys, argv);
            return UNLOCK_SUCCESS_CODE.equals(result);
        }catch (JedisNoScriptException e){
            //没有脚本缓存时，重新发送缓存
            logger.info("try to store script......");
            storeScript(lockKey);
            Object result = jedisCluster.evalsha(unlockSha1, keys, argv);
            return UNLOCK_SUCCESS_CODE.equals(result);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 由于使用redis集群，因此每个节点都需要各自缓存一份脚本数据
     * @param slotKey 用来定位对应的slot的slotKey
     */
    public void storeScript(String slotKey){
        if (StringUtils.isEmpty(unlockSha1) || !jedisCluster.scriptExists(unlockSha1, slotKey)){
            //redis支持脚本缓存，返回哈希码，后续可以继续用来调用脚本
            unlockSha1 = jedisCluster.scriptLoad(DISTRIBUTE_LOCK_SCRIPT_UNLOCK_VAL, slotKey);
        }
    }
}

