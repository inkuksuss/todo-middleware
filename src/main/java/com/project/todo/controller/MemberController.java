package com.project.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    DataSource dataSource;

    @GetMapping("/hello")
    public String hello() throws SQLException {
        log.info("datasorure= {}", dataSource.getConnection().getMetaData());

        return "hello";
    }

    @PostMapping("/test")
    public String test() {
        return "test";
    }
}
