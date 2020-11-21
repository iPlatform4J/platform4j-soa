package com.platform4j.soa.service;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {
        "classpath:/applicationContext-resources.xml",
        "classpath:/applicationContext-dao.xml",
        "classpath:/applicationContext-mongo.xml",
        "classpath:/applicationContext-aop.xml",
        "classpath:/applicationContext-tx.xml",
        "classpath:/applicationContext-redis.xml",
        "classpath:/dubbo-provider.xml",
        "classpath:/dubbo-consumer.xml",
        "classpath:/kafka-consumer.xml",
        "classpath:/kafka-provider.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneralSpringTest {

//    public Log log = LogFactory.getLog(this.getClass());

    @Test
    public  void test(){
    }
}
