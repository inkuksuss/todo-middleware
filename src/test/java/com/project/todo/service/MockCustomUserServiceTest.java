package com.project.todo.service;

import com.project.todo.config.security.provider.JwtTokenProvider;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
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
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@Slf4j
public class MockCustomUserServiceTest {

    @InjectMocks
    private CustomUserService customUserService;

    @Mock
    private MemberRepository memberRepository;

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
    @DisplayName("loadUserByUsername() 정상")
    void loadUserByUsername() {
        //given
        Member testMember = new Member( "test1", "test@naver.com", "1111", MEMBER_TYPE.MEMBER);
        testMember.forceChangeId(1L);
        Optional<Member> mockResponse = Optional.of(testMember);
        when(memberRepository.findById(anyLong())).thenReturn(mockResponse);

        // when
        MemberContext findMember
                = (MemberContext) customUserService.loadUserByUsername("1");

        //then
        assertThat(findMember.getMember().getId()).isEqualTo(1L);
        assertThat(findMember.getMember().getType()).isEqualTo(MEMBER_TYPE.MEMBER);
    }

    @Test
    @DisplayName("loadUserByUsername() invalid PK")
    void loadByUsernameInvalidPK() {
        // given
        String memberId = "sadsad";
        // when
        // then
        assertThatThrownBy(() -> customUserService.loadUserByUsername(memberId))
                .isInstanceOf(InsufficientAuthenticationException.class);
    }

    @Test
    @DisplayName("loadUserByUsername() empty string")
    void loadByUsernameNullPk() {
        // given
        String emptyId = "";
        String nullId = null;
        // when
        // then
        assertThatThrownBy(() -> customUserService.loadUserByUsername(emptyId))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> customUserService.loadUserByUsername(nullId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
