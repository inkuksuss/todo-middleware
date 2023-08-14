package com.project.todo.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class AuthPointcuts {

    @Pointcut("@annotation(com.project.todo.aop.annotation.Login)")
    public void login() {}


}
