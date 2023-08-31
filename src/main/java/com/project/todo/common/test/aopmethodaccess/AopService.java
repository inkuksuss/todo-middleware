package com.project.todo.common.test.aopmethodaccess;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AopService {

    public void publicMethod() {
        log.info("call public");

        protectedMethod();
        defaultMethod();
        privateMethod();
    }

    protected void protectedMethod() {
        log.info("call protect");
    }

    void defaultMethod() {
        log.info("call default");
    }

    private void privateMethod() {
        log.info("call private");
    }
}
