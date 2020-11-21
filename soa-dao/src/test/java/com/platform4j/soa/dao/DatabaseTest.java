package com.platform4j.soa.dao;

import com.platform4j.soa.dao.mysql.DatabaseMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DatabaseTest {

    private static DatabaseMapper datebaseMapper;

    public static void main(String[] args){
        String[] locations = {"applicationContext-dao.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(locations);
        datebaseMapper = (DatabaseMapper) ctx.getBean("databaseMapper");
        test();
    }

    private static void test(){
        datebaseMapper.queryHelloById("10040421108001");
    }
}
