package com.project.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todo.domain.request.MemberDetailRequest;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Slf4j
class MemberControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    private final String prefix = "/api/member";

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void join_success() {
        String requestUrl = "/join";
        MemberDetailRequest testData = new MemberDetailRequest(null, "hello", "test@naver.com", "1111");

        ResponseEntity<ResponseResult> response = this.restTemplate.postForEntity("http://localhost:" + port + prefix + requestUrl, testData, ResponseResult.class);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo(0);
        Assertions.assertThat(response.getBody().getData()).isNull();
    }

    @Test
    void join_same_email() {
        String requestUrl = "/join";

        MemberDetailRequest testData1 = new MemberDetailRequest(null, "hello", "test@naver.com", "1111");
        this.restTemplate.postForEntity("http://localhost:" + port + prefix + requestUrl, testData1, ResponseResult.class);

        MemberDetailRequest testData2 = new MemberDetailRequest(null, "hello2", "test@naver.com", "11112");
        ResponseEntity<ResponseResult> response = this.restTemplate.postForEntity("http://localhost:" + port + prefix + requestUrl, testData2, ResponseResult.class);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo(1);
        Assertions.assertThat(response.getBody().getData()).isNull();
    }

    @Test
    public void mockingTest() {

//        log.info("port = {} server = {}", port, serverUrl);
        // given
        MemberDetailRequest testData = new MemberDetailRequest(null, null, "test@naver.com", "1111");

        String result = this.restTemplate.postForObject("http://localhost:" + port + "/api/member" + "/login", testData, String.class);

        log.info("result = {}", result);


        // when
        // then
    }
}