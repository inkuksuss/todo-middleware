package com.project.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.request.JoinRequest;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@ExtendWith(MockitoExtension.class)
class MockMemberControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("")
    void join_success() throws Exception {
        // given
        JoinRequest request = new JoinRequest("ee@naver.com", "dd", "11");

        MemberDto form = new MemberDto();
        form.setEmail("ee@naver.com");
        form.setName("dd");
        form.setPassword("11");

        MemberDto resDto = new MemberDto();
        resDto.setId(1L);
        resDto.setEmail("ee@naver.com");
        resDto.setName("dd");
        resDto.setPassword("11");
        resDto.setCreated(LocalDateTime.now());
        resDto.setUpdated(LocalDateTime.now());
        resDto.setIsDelete("N");

        doReturn(resDto).when(memberService).doJoin(any(MemberDto.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("")
    void invalid_email() throws Exception {
        // given
        JoinRequest request = new JoinRequest("ee", "dd", "11");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("")
    void invalid_param() throws Exception {
        // given
        JoinRequest request = new JoinRequest("ee", "dd", null);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

}