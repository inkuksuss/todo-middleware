package com.project.todo.test.postprocessor.code;


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


@Slf4j
public class PostProcessorObject implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AopTarget) log.info("call before init");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AopTarget) log.info("call after init = {}", beanName);

        if (!(bean instanceof AopTarget)) {
            return bean;
        }

        ProxyFactory proxyFactory = new ProxyFactory(bean);
        PostProcessorAdvice advice = new PostProcessorAdvice();
        proxyFactory.addAdvice(advice);
        Object proxy = proxyFactory.getProxy();

        log.info("call after complete");

        return proxy;
    }
}
