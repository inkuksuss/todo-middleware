package com.project.todo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OauthController {

    @GetMapping("/oauth/code/google")
    public void test(HttpServletRequest request) {

        log.info("request = {}", request.getParameter("code"));
    }
}
