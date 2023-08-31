package com.project.todo.common.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class AuthPointcuts {

    @Pointcut("@annotation(com.project.todo.common.aop.annotation.Login)")
    public void login() {}


}
