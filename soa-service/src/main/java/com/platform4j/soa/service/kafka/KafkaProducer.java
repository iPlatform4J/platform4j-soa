package com.platform4j.soa.service.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.Random;

public class KafkaProducer {
    private static Logger logger = Logger.getLogger(KafkaProducer.class);
    private static Producer<String, String> producer;
    private static Properties properties = new Properties();
    private Random rnd = new Random();
    private String metadataBrokerList;

    private KafkaProducer(String brokerList){
        this.metadataBrokerList = brokerList;
        this.init();
    }

    public void sendData(String topic, String msg) {
        try {
            // 产生并发送消息
            String key = rnd.nextInt(255) + "";
            //如果topic不存在，则会自动创建，默认replication-factor为1，partitions为0
            KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, key, msg);
            producer.send(data);
        } catch (Exception e) {
            logger.error("KafkaProducer.sendData Exception!! topic=" + topic + " msg" + msg, e);
        }
    }

    private void init(){
        Properties prop = new Properties();
        prop.put("serializer.class","kafka.serializer.StringEncoder");
        prop.put("metadata.broker.list",metadataBrokerList);
        prop.put("key.serializer.class","kafka.serializer.StringEncoder");
        prop.put("request.required.acks","1");
        prop.put("producer.type","sync");
        prop.put("message.send.max.retries","3");
        ProducerConfig producerConfig = new ProducerConfig(prop);
        producer = new Producer(producerConfig);
    }

    public void close() {
        if (producer != null) {
            producer.close();
        }
    }

    public void setProducer(Producer<String, String> producer) {
        this.producer = producer;
    }
}
