package com.platform4j.soa.dao.redis;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

public interface LettuceDao {
    RedisAdvancedClusterCommands<byte[],byte[]> getSync();

    public StatefulRedisClusterConnection<byte[],byte[]> getConnect();

    public byte[] getObj(String key);

    public String setObj(String key,byte[] data);

    public RedisFuture<Long> asyncDelObj(String key);

    public String setObjEx(String key,byte[] data,long expire);

    public boolean setNx(final String key,byte[] data);

    public Boolean hset(String key,String field,byte[] data);

    public byte[] hget(String key,String field);

    public Long hdel(String key,String ... field);

    public Long asyncHdel(String key,String ... field);

    public void destroy();

    public Boolean expire(String key,long expire);

    public Long exists(String key);

    public Long incrBy(String key, long count);

    public Long incr(String key);

    public Long decrBy(String key, long count);

    public Long decr(String key);

    public Long del(String key);
}
