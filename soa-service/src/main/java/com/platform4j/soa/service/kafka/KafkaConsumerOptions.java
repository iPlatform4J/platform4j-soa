package com.platform4j.soa.service.kafka;

public class KafkaConsumerOptions {
    private String zkSessionTimeout;
    private String zkConnectionTimeout;
    private String zkSyncTime;
    private String autoCommitInterval;
    private String autoOffsetReset;
    private String rebalanceBackOff;
    private String rebalanceMaxRetries;

    public String getZkSessionTimeout() {
        return zkSessionTimeout;
    }

    public void setZkSessionTimeout(String zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }

    public String getZkConnectionTimeout() {
        return zkConnectionTimeout;
    }

    public void setZkConnectionTimeout(String zkConnectionTimeout) {
        this.zkConnectionTimeout = zkConnectionTimeout;
    }

    public String getZkSyncTime() {
        return zkSyncTime;
    }

    public void setZkSyncTime(String zkSyncTime) {
        this.zkSyncTime = zkSyncTime;
    }

    public String getAutoCommitInterval() {
        return autoCommitInterval;
    }

    public void setAutoCommitInterval(String autoCommitInterval) {
        this.autoCommitInterval = autoCommitInterval;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public String getRebalanceBackOff() {
        return rebalanceBackOff;
    }

    public void setRebalanceBackOff(String rebalanceBackOff) {
        this.rebalanceBackOff = rebalanceBackOff;
    }

    public String getRebalanceMaxRetries() {
        return rebalanceMaxRetries;
    }

    public void setRebalanceMaxRetries(String rebalanceMaxRetries) {
        this.rebalanceMaxRetries = rebalanceMaxRetries;
    }

    @Override
    public String toString() {
        return "KafkaConsumerOptions{" +
                "zkSessionTimeout='" + zkSessionTimeout + '\'' +
                ", zkConnectionTimeout='" + zkConnectionTimeout + '\'' +
                ", zkSyncTime='" + zkSyncTime + '\'' +
                ", autoCommitInterval='" + autoCommitInterval + '\'' +
                ", autoOffsetReset='" + autoOffsetReset + '\'' +
                ", rebalanceBackOff='" + rebalanceBackOff + '\'' +
                ", rebalanceMaxRetries='" + rebalanceMaxRetries + '\'' +
                '}';
    }
}
