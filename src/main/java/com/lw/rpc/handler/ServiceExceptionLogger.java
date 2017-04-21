package com.lw.rpc.handler;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * 异常结果来
 * 
 * @author shangdc
 *
 */
public class ServiceExceptionLogger implements ThrowsAdvice {

    private Logger LOG = Logger.getLogger(this.getClass().getName());

    // 重写afterThrowing()方法
    public void afterThrowing(Method method, Object[] args, Object target, Throwable subclass) throws Throwable {
        LOG.error("RUN " + target.getClass() + "." + method.getName() + "  throws exception " + subclass);
    }
}
