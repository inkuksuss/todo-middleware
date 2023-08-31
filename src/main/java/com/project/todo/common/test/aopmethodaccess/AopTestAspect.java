package com.project.todo.common.test.aopmethodaccess;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class AopTestAspect {

    @Pointcut("execution(* com.project.todo.test.aopmethodaccess.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void beforePointcut() {
        log.info("aop advice call");
    }
}
