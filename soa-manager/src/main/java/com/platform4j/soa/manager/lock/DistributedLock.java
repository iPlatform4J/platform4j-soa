package com.platform4j.soa.manager.lock;

public interface DistributedLock {

    /**
     * 尝试占Redis锁，若锁被占用则直接返回失败
     * @param key
     * @return
     */
    boolean tryRedisLock(String key);

    /**
     * 尝试占Redis锁，若锁被占用则循环调用，如果time时间内仍为成功则返回失败
     * @param key
     * @param time SECONDS
     * @return
     */
    boolean occupyRedisLock(String key, long time);

    /**
     * 释放Redis锁
     * @param key
     * @return
     */
    boolean releaseRedisLock(String key);

    /**
     * 尝试占ZK锁，若锁被占用则直接返回失败
     * @param key
     * @return
     */
    boolean tryCuratorLock(String key);

    /**
     * 尝试占ZK锁，若锁被占用则循环调用，如果time时间内仍为成功则返回失败
     * @param key
     * @param time
     * @return
     */
    boolean occupyCuratorLock(String key, long time);

    /**
     * 释放ZK锁
     * @param key
     * @return
     */
    boolean releaseCuratorLock(String key);
}
