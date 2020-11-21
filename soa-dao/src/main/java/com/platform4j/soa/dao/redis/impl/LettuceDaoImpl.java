package com.platform4j.soa.dao.redis.impl;

import com.platform4j.soa.dao.redis.LettuceDao;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class LettuceDaoImpl implements LettuceDao {
    @Autowired
    private RedisClusterClient clusterClient;
    private StatefulRedisClusterConnection<byte[],byte[]> connection;

    @PostConstruct
    public void init(){
        connection = clusterClient.connect(ByteArrayCodec.INSTANCE);
    }

    public StatefulRedisClusterConnection<byte[],byte[]> getConnect(){
        return connection;
    }

    public void destroy(){
        connection.close();
        clusterClient.shutdown();
    }

    public RedisAdvancedClusterCommands<byte[],byte[]> getSync() {
        return connection.sync();
    }

    public byte[] getObj(final String key) {
        return connection.sync().get(key.getBytes());
    }

    public String setObj(final String key, byte[] data) {
        return connection.sync().set(key.getBytes(), data);
    }

    public RedisFuture<Long> asyncDelObj(String key) {
        return connection.async().del(key.getBytes());
    }

    public String setObjEx(String key, byte[] data, long expire) {
        return connection.sync().setex(key.getBytes(), expire, data);
    }

    public boolean setNx(String key, byte[] data) {
        return connection.sync().setnx(key.getBytes(), data);
    }

    public Boolean hset(String key, String field, byte[] data) {
        return connection.sync().hset(key.getBytes(),field.getBytes(),data);
    }

    public byte[] hget(String key, String field) {
        return connection.sync().hget(key.getBytes(),field.getBytes());
    }

    public Long hdel(String key, String ... field) {
        byte[][] temp = new byte[field.length][];
        for (int i = 0; i < field.length; i++) {
            temp[i] = field[i].getBytes();
        }
        return connection.sync().hdel(key.getBytes(),temp);
    }


    public Long asyncHdel(String key, String... field) {
        byte[][] temp = new byte[field.length][];
        for (int i = 0; i < field.length; i++) {
            temp[i] = field[i].getBytes();
        }
        return connection.sync().hdel(key.getBytes(),temp);
    }

    public Boolean expire(String key,long expire){
        return connection.sync().expire(key.getBytes(), expire);
    }

    public Long exists(String key) {
        return connection.sync().exists(key.getBytes());
    }

    public Long incrBy(String key, long count) {
        return connection.sync().incrby(key.getBytes(),count);
    }

    public Long incr(String key) {
        return connection.sync().incr(key.getBytes());
    }

    public Long decrBy(String key, long count) {
        return connection.sync().decrby(key.getBytes(), count);
    }

    public Long decr(String key) {
        return connection.sync().decr(key.getBytes());
    }

    public Long del(String key) {
        return connection.sync().del(key.getBytes());
    }
}
