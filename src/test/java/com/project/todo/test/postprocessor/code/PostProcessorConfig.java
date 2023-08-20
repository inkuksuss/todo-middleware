package com.project.todo.test.postprocessor.code;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessorConfig {

    @Bean
    public PostProcessorObject postProcessorObject() {
        return new PostProcessorObject();
    }

    @Bean
    public AopTarget aopTarget() {
        return new AopTarget();
    }
}
