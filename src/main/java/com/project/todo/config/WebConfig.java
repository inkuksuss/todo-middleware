package com.project.todo.config;

import com.project.todo.config.argument_resolver.LoginIdArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LoginIdArgumentResolver loginIdArgumentResolver() {
        return new LoginIdArgumentResolver();
    }

    @Bean
    public LoginMemberArgumentResolver loginMemberArgumentResolver() {
        return new LoginMemberArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginIdArgumentResolver());
        resolvers.add(loginMemberArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name());
    }
}
