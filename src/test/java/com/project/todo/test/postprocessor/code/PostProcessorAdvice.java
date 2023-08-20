package com.project.todo.test.postprocessor.code;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


@Slf4j
public class PostProcessorAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        log.info("call invoke start");
        Object proceed = invocation.proceed();
        log.info("call invoke end");

        return proceed;
    }
}
