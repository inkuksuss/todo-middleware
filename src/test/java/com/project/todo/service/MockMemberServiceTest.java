package com.project.todo.service;

import com.project.todo.config.security.provider.JwtTokenProvider;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.repository.member.MemberRepository;
import com.project.todo.service.security.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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

    @Spy
    private CustomUserService customUserService = new CustomUserService(memberRepository);

    @Spy
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(customUserService, new BCryptPasswordEncoder(), "zJ9xHgTxbsj4QQGUWDuxpWSHKq8RQVPkashUHsuvur3oA9djvivjfij2Adnsici2nfkaz9x4sksdbij3i");

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
    @DisplayName("join success")
    void doJoinSuccess() {
        // given
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test1");
        memberDto.setEmail("test@naver.com");
        memberDto.setPassword("1111");
        memberDto.setType(MEMBER_TYPE.MEMBER);

        Member mockResponse = new Member(
                1L,
                memberDto.getName(),
                memberDto.getEmail(),
                jwtTokenProvider.encryptPassword(memberDto.getPassword()),
                MEMBER_TYPE.MEMBER
        );

        when(memberRepository.save(any(Member.class))).thenReturn(mockResponse);

        // when
        MemberDto savedMember = memberService.doJoin(memberDto);

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo(memberDto.getName());
        assertThat(savedMember.getEmail()).isEqualTo(memberDto.getEmail());
        assertThat(savedMember.getType()).isEqualTo(memberDto.getType());
    }

    @Test
    @DisplayName("join invalid email")
    void doJoinInvalidEmail() {
        // given
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test1");
        memberDto.setEmail("");
        memberDto.setPassword("1111");
        memberDto.setType(MEMBER_TYPE.MEMBER);

        assertThatThrownBy(() -> memberService.doJoin(memberDto)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("join duplicate email")
    void doJoinDuplicateEmail() {
        // given
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test1");
        memberDto.setEmail("asdasd@naver.com");
        memberDto.setPassword("1111");
        memberDto.setType(MEMBER_TYPE.MEMBER);

        Optional<Member> mockResponse = Optional.of(
                new Member(1L, memberDto.getName(), memberDto.getEmail(), memberDto.getPassword(), MEMBER_TYPE.MEMBER));

        //when
        when(memberRepository.findByEmail(anyString())).thenReturn(mockResponse);

        //then
        assertThatThrownBy(() -> memberService.doJoin(memberDto)).isInstanceOf(DuplicateEmailException.class);
    }
}