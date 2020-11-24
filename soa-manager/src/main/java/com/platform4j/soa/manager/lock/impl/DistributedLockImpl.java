package com.platform4j.soa.manager.lock.impl;

import com.platform4j.soa.dao.redis.RedisObjectDao;
import com.platform4j.soa.manager.lock.DistributedLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DistributedLockImpl implements DistributedLock {
    private final Log logger = LogFactory.getLog(DistributedLockImpl.class);

    private static String redisLockPrefix = "_REDISLOCK";
    private static long redisLockExpire = 60;

    @Autowired
    private RedisObjectDao redisObjectDao;


    public boolean tryRedisLock(String key){
        return redisObjectDao.tryDistributedLock(key, redisLockPrefix+key, redisLockExpire);
    }

    public boolean occupyRedisLock(String key, long time){
        if(time <= 0){
            return false;
        }
        long t = 0L;
        try{
            while (t < time){
                if(tryRedisLock(key)){
                    break;
                }
                Thread.sleep(1000);
                t++;
            }
            return t < time;
        }catch(Exception e){
            logger.error("occupyRedisLock error: {}", e);
        }
        return false;
    }

    public boolean releaseRedisLock(String key){
        return redisObjectDao.removeDistributedLock(key, redisLockPrefix+key);
    }

    public boolean tryCuratorLock(String key){
        return CuratorLock.acquireLock(key, 1);
    }

    public boolean occupyCuratorLock(String key, long time){
        return CuratorLock.acquireLock(key, time);
    }

    public boolean releaseCuratorLock(String key){
        return CuratorLock.releaseLock(key);
    }
}
