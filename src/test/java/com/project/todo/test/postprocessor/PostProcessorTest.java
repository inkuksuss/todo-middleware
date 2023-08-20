package com.project.todo.test.postprocessor;

import com.project.todo.test.postprocessor.code.AopTarget;
import com.project.todo.test.postprocessor.code.PostProcessorConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostProcessorConfig.class)
@Slf4j
public class PostProcessorTest {

    @Autowired
    AopTarget aopTarget;

    @Test
    void call() {
        log.info("is proxy = {}", AopUtils.isAopProxy(aopTarget));
        aopTarget.test();
    }
}
