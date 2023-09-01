package com.project.todo.config.security.handler;

import com.project.todo.common.utils.URLUtils;
import com.project.todo.config.security.provider.JwtTokenProvider;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.model.member.MemberAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;


@Slf4j
public class OAuthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String DEFAULT_REDIRECT_URL = "http://localhost:4000/oauth/redirect";

    private final JwtTokenProvider jwtTokenProvider;

    public OAuthAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("auth2 = {}", authentication.getPrincipal().toString());
        MemberAuthentication memberAuthentication = (MemberAuthentication) authentication.getPrincipal();
        MemberDto member = memberAuthentication.getMember();

        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail(), authentication.getAuthorities());
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("token", token);
        String targetUrl = URLUtils.createUrl(DEFAULT_REDIRECT_URL, queryParams);

        if (response.isCommitted()) {
            this.logger.debug(LogMessage.format("Did not redirect to %s since response already committed.", targetUrl));
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
