package com.project.todo.test.postprocessor.code;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AopTarget {

    public void test() {
        log.info("call test");
    }

    @PostConstruct
    public void init() {
        log.info("call post cons");
    }

    @PreDestroy
    public void destroy() {
        log.info("call destroy");
    }
}
