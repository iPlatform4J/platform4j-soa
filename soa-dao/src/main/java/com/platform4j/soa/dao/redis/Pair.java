package com.platform4j.soa.dao.redis;

public class Pair<K, Object> {

    private K key;
    private Object value;

    public Pair(K key, Object value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
