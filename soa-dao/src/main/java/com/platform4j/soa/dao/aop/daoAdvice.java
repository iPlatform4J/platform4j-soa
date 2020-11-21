package com.platform4j.soa.dao.aop;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class daoAdvice implements MethodInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(daoAdvice.class.getName());

    public Object invoke(MethodInvocation invocation) throws Throwable{
        //用 commons-lang 提供的 StopWatch 计时，Spring 也提供了一个 StopWatch
        StopWatch clock = new StopWatch();
        clock.start(); //计时开始
        Object result = invocation.proceed();
        clock.stop();  //计时结束

        //方法参数类型，转换成简单类型
        Class[] params = invocation.getMethod().getParameterTypes();
        String[] simpleParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            simpleParams[i] = params[i].getSimpleName();
        }

        //大于3s 的请求，先打error 日志，方便性能优化
        if(clock.getTotalTimeMillis() >=3000) {
            logger.error("该方法执行耗费: " + clock.getTotalTimeMillis() + " ms ["
                    + invocation.getThis().getClass().getName() + "."
                    + invocation.getMethod().getName() + "("+ StringUtils.join(simpleParams,",")+")] ");
        }else if(clock.getTotalTimeMillis() >=1000) {
            logger.warn("该方法执行耗费: " + clock.getTotalTimeMillis() + " ms ["
                    + invocation.getThis().getClass().getName() + "."
                    + invocation.getMethod().getName() + "("+ StringUtils.join(simpleParams,",")+")] ");
        }else {
            logger.info("该方法执行耗费: " + clock.getTotalTimeMillis() + " ms ["
                    + invocation.getThis().getClass().getName() + "."
                    + invocation.getMethod().getName() + "("+StringUtils.join(simpleParams,",")+")] ");
        }
        return result;
    }
}
