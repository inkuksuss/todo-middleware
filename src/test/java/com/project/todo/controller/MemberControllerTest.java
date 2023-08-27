package com.project.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.todo.domain.request.member.JoinRequest;
import com.project.todo.domain.request.member.LoginRequest;
import com.project.todo.domain.response.MemberDetailResponse;
import com.project.todo.domain.response.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Slf4j
class MemberControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    private final String prefix = "/api/member";

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    void join_success() {
        //given
        String requestUrl = "/join";
        JoinRequest testData = new JoinRequest("asd@naver.com", "test", "1111");

        //when
        ResponseEntity<ResponseResult> response = this.restTemplate.postForEntity("http://localhost:" + port + prefix + requestUrl, testData, ResponseResult.class);

        //then
        log.info("response = {}", response);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo(0);
        Assertions.assertThat(response.getBody().getData()).isNull();
    }

    @Test
    void join_same_email() {
        //given
        String requestUrl = "/join";
        JoinRequest testData1 = new JoinRequest("asd@naver.com", "test", "1111");
        this.restTemplate.postForEntity("http://localhost:" + port + prefix + requestUrl, testData1, ResponseResult.class);

        // when
        JoinRequest testData2 = new JoinRequest("asd@naver.com", "test2", "1111");
        ResponseEntity<ResponseResult> response = this.restTemplate.postForEntity("http://localhost:" + port + prefix + requestUrl, testData2, ResponseResult.class);

        //then
        log.info("response = {}", response.getBody());
        Assertions.assertThat(response.getBody().getCode()).isEqualTo(1);
        Assertions.assertThat(response.getBody().getData()).isNull();
    }

    @Test
    void login_success() throws JsonProcessingException {
        //given
        String joinReqeust = "/join";
        JoinRequest testData1 = new JoinRequest("asd@naver.com", "test", "1111");
        this.restTemplate.postForEntity("http://localhost:" + port + prefix + joinReqeust, testData1, ResponseResult.class);

        //when
        String requestUrl = "/login";
        LoginRequest testData = new LoginRequest( "asd@naver.com", "1111");
        ResponseEntity<ResponseResult> response = this.restTemplate.postForEntity("http://localhost:" + port + prefix + requestUrl, testData, ResponseResult.class);

        MemberDetailResponse responseData = objectMapper.convertValue(response.getBody().getData(), MemberDetailResponse.class);
        Assertions.assertThat(response.getBody().getCode()).isEqualTo(0);
        Assertions.assertThat(responseData.getToken()).isNotNull();
    }
}