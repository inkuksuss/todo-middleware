package com.project.todo.config.security.provider;

import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.service.security.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Set;


@Slf4j
class JwtTokenProviderTest {

    JwtTokenProvider jwtTokenProvider;

    @Mock
    CustomUserService customUserService;


    private AutoCloseable closeable;

    @BeforeEach
    void beforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
        this.jwtTokenProvider = new JwtTokenProvider(customUserService, new BCryptPasswordEncoder(), "zJ9xHgTxbsj4QQGUWDuxpWSHKq8RQVPkashUHsuvur3oA9djvivjfij2Adnsici2nfkaz9x4sksdbij3i");
    }

    @AfterEach
    void afterEach() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("token 생성")
    void createToken() {
        // given
        Long memberId = 1L;
        String email = "test@naver.com";
        Collection<GrantedAuthority> roles = Set.of(new SimpleGrantedAuthority(MEMBER_TYPE.MEMBER.getRole()));

        // when
        String token = jwtTokenProvider.createToken(memberId, email, roles);

        // then
        Assertions.assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("token valid")
    void validToken() {
        // given
        Long memberId = 1L;
        String email = "test@naver.com";
        Collection<GrantedAuthority> roles = Set.of(new SimpleGrantedAuthority(MEMBER_TYPE.MEMBER.getRole()));
        String token = jwtTokenProvider.createToken(memberId, email, roles);

        // when
        boolean isValid = jwtTokenProvider.validateToken(token);

        // then
        Assertions.assertThat(isValid).isTrue();
    }

}