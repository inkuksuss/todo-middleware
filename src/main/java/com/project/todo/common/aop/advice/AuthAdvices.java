package com.project.todo.common.aop.advice;


import com.project.todo.domain.model.member.MemberPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class AuthAdvices {

    @Before("com.project.todo.common.aop.pointcut.AuthPointcuts.login()")
    public void checkLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("authentication = {}", authentication);
        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        log.info("principal = {}", principal);

//        if (principal == null || principal instanceof String) {
//            throw new AccessDeniedException("can not find user");
//        }
    }
}
