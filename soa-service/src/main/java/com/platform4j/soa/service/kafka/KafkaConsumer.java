package com.platform4j.soa.service.kafka;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KafkaConsumer {
    private static final Logger logger= Logger.getLogger(KafkaConsumer.class);
    private ConsumerConnector consumer;
    private String topic;
    private ExecutorService executor;
    private volatile int threadNumber = 0;

    public KafkaConsumer(String a_zookeeper, String a_groupId, String a_topic) {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig(a_zookeeper, a_groupId));
        this.topic = a_topic;
    }

    public void shutdown() {
        if (consumer != null) consumer.shutdown();
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                logger.info("KafkaConsumer shutdown: Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            logger.info("KafkaConsumer shutdown: Interrupted during shutdown, exiting uncleanly");
        }
    }

    /**
     * 订阅主题
     * @param topic
     */
    public void subscribe(String topic) {
        this.topic = topic;
    }

    /**
     * 多线程消费，建议和partition数量一致
     */
    public void run(int partitionNum) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(partitionNum));// 描述读取哪个topic，需要几个线程读
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);// 创建Streams
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);// 每个线程对应于一个KafkaStream
        // now launch all the threads
        executor = Executors.newFixedThreadPool(streams.size());
        // now create an object to consume the messages
        try {
            for (final KafkaStream stream : streams) {
                if (StringUtils.equals(topic, "kafkaTopic")) {
                    executor.submit(new Runnable() {
                        public void run() {
                            ConsumerIterator<byte[], byte[]> it = stream.iterator();
                            while (it.hasNext()){
                                StringBuilder message = new StringBuilder();
                                message.append("consuming topic[").append(topic).append("] ");
                                String url = new String(it.next().message());
                                try {
                                    logger.info(message.append("the message is: ").append(url));
                                    //todo: consumer logic
                                } catch (Exception e) {
                                    logger.error(message.append("kafka message consumer catch exception: "), e);
                                }
                            }
                        }
                    });
                    threadNumber++;
                }
            }
        } catch (Exception e) {
            logger.error("Run message consumer catch Exception. "+e);
        }
    }

    private ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", a_zookeeper);
        props.put("group.id", a_groupId);
        props.put("zookeeper.sync.time.ms", "2000");
        props.put("zookeeper.session.timeout.ms","5000");
        props.put("zookeeper.connection.timeout.ms","6000");
        props.put("auto.commit.interval.ms","1000");
        props.put("auto.offset.reset","smallest");
        props.put("rebalance.max.retries","50");
        props.put("rebalance.backoff.ms","1200");
        return new ConsumerConfig(props);
    }
}
