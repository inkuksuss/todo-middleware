package com.project.todo.service;

import com.project.todo.domain.dto.MemberContext;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
class MockMemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void beforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        closeable.close();
    }

    @Test
    void doJoin() {
        //given
        Member testMember = new Member(1L, "test1", "test@naver.com", "1111", MEMBER_TYPE.MEMBER);
        Optional<Member> mockResponse = Optional.of(testMember);
        when(memberRepository.findByEmail(anyString())).thenReturn(mockResponse);

        // when
        MemberContext hello = (MemberContext) memberService.loadUserByUsername("hello");

        //then
        assertThat(hello.getMember().getType()).isEqualTo(MEMBER_TYPE.MEMBER);
    }
}