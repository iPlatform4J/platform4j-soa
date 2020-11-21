package com.platform4j.soa.service.hello;

import com.platform4j.soa.api.HelloService;
import com.platform4j.soa.domain.Hello;
import org.springframework.stereotype.Component;

@Component("helloService")
public class HelloServiceImpl implements HelloService {

    public Hello sayHello(String hello){
//        try{
//            Thread.sleep(5000);
//        }catch(Exception e){
//        }
        return new Hello(hello);
    }
}
