package com.platform4j.soa.manager.lock.impl;

import com.platform4j.soa.manager.base.CuratorBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

public class CuratorLock extends CuratorBase {

    private static final Log logger = LogFactory.getLog(CuratorLock.class);

    /**
     * 占用锁
     * @param key
     * @param time
     * @return
     */
    public static boolean acquireLock(String key, long time){
        int flag = 1;
        try {
            InterProcessMutex lock = getMutexLock(key);
            while (!lock.acquire(1, TimeUnit.SECONDS)){
                flag++;
                if(flag > time){
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("distributed lock " + key + " acquire exception="+e);
        }
        if(flag > time){
            logger.info("Curator distributed lock " + key + " busy");
            return false;
        }
        logger.info("Curator acquire distributed lock " + key + " success");
        return true;
    }

    /**
     * 释放锁
     * @param key
     * @return
     */
    public static boolean releaseLock(String key){
        try {
            InterProcessMutex lock = getMutexLock(key);
            if(lock.isAcquiredInThisProcess()){
                lock.release();
                framework.delete().inBackground().forPath(buildPath(key));
                logger.info("Curator release distributed lock " + key + " success");
                return true;
            }
        }catch (Exception e){
            logger.error("Curator release distributed lock " + key + " error, {}", e);
        }
        return false;
    }


    /**
     * 获取互斥锁
     * @param name
     * @return
     * @throws Exception
     */
    private static InterProcessMutex getMutexLock(String name) throws Exception {
        return new InterProcessMutex(framework, buildPath(name));
    }
}
