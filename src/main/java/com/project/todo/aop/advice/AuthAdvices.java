package com.project.todo.aop.advice;


import com.project.todo.domain.dto.MemberContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class AuthAdvices {

    @Before("com.project.todo.aop.pointcut.AuthPointcuts.login()")
    public void checkLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal == null || principal instanceof String) {
            throw new AccessDeniedException("can not find user");
        }
    }
}
