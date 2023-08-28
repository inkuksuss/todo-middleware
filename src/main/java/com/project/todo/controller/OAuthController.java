package com.project.todo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@RestController
public class OAuthController {

    @GetMapping("/oauth/code/google")
    public void test(HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        Enumeration<String> parameterNames = request.getParameterNames();
        parameterNames.asIterator().forEachRemaining(name -> log.info("request.getParameter(name) = {}, {}", name, request.getParameter(name)));
//        log.info("request = {}", request.getParameter("code"));
    }

    @GetMapping("/google")
    public void redirectTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        parameterNames.asIterator().forEachRemaining(name -> log.info("request.getParameter(name) = {}, {}", name, request.getParameter(name)));
    }
}
