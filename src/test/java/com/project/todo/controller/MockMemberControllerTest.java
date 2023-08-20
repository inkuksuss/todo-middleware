package com.project.todo.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.request.member.JoinRequest;
import com.project.todo.domain.request.member.LoginRequest;
import com.project.todo.domain.response.MemberDetailResponse;
import com.project.todo.domain.types.RESPONSE_CODE;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
class MockMemberControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .build();
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

    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        // given
        LoginRequest request = new LoginRequest("ee@naver.com", "1123");

        MemberDto resDto = new MemberDto();
        resDto.setId(1L);
        resDto.setEmail("ee@naver.com");
        resDto.setName("dd");
        resDto.setCreated(LocalDateTime.now());
        resDto.setUpdated(LocalDateTime.now());
        resDto.setIsDelete("N");

        given(memberService.doLogin(anyString(), anyString()))
                .willReturn(resDto);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions
                .andExpect(status().isOk());
//                        .andDo(print());

        String  contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        JavaType returnType = this.objectMapper.getTypeFactory().constructParametricType(ResponseResult.class, MemberDetailResponse.class);
        ResponseResult<MemberDetailResponse> result = this.objectMapper.readValue(contentAsString, returnType);
        log.info(" result = {}", result.toString());

        assertThat(result.getCode()).isEqualTo(RESPONSE_CODE.SUCCESS.getCode());
        assertThat(result.getData().getName()).isEqualTo(resDto.getName());
        assertThat(result.getData().getEmail()).isEqualTo(request.getEmail());
        verify(memberService, times(1)).doLogin(anyString(), anyString());
    }

    @Test
    @DisplayName("파라미터 누락")
    void login_invalid_param() throws Exception {
        // given
        LoginRequest request = new LoginRequest("ee@naver.com", null);

        MemberDto resDto = new MemberDto();
        resDto.setId(1L);
        resDto.setEmail("ee@naver.com");
        resDto.setName("dd");
        resDto.setCreated(LocalDateTime.now());
        resDto.setUpdated(LocalDateTime.now());
        resDto.setIsDelete("N");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions
                .andExpect(status().isBadRequest());
    }

}