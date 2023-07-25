package com.project.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/test")
    public String test() {
        return "test";
    }
}
