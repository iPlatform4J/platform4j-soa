package com.platform4j.soa.service.Launcher;

import com.platform4j.soa.service.kafka.KafkaConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    private static Log logger = LogFactory.getLog(Launcher.class);

    private static volatile boolean running = true;

    private static ApplicationContext ctx;

    public static void main(String[] args){
        try{
            ctx = new ClassPathXmlApplicationContext(new String[]{
                    "applicationContext-resources.xml",
                    "applicationContext-dao.xml",
                    "applicationContext-mongo.xml",
                    "applicationContext-aop.xml",
                    "applicationContext-tx.xml",
                    "applicationContext-redis.xml",
                    "dubbo-provider.xml",
                    "dubbo-consumer.xml",
                    "kafka-consumer.xml",
                    "kafka-provider.xml"
            });
            logger.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server started!");

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            //提交线程时设置线程数量1，切勿轻易改动
            executorService.submit(new Runnable() {
                public void run() {
                    KafkaConsumer kafkaConsumer = (KafkaConsumer) ctx.getBean("kafkaConsumer");
                    kafkaConsumer.run(1);
                }
            });
        }catch(Exception e){
            running = false;
            logger.error(e.getMessage(), e);
        }
        synchronized (Launcher.class) {
            while (running) {
                try {
                    Launcher.class.wait();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
