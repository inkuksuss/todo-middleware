package com.project.todo.config.security.handler;

import com.project.todo.common.utils.URLUtils;
import com.project.todo.config.security.provider.JwtTokenProvider;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.model.member.MemberPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.*;


@Slf4j
public class OAuthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String DEFAULT_REDIRECT_URL = "http://localhost:4000/oauth/redirect";

    private final JwtTokenProvider jwtTokenProvider;

    public OAuthAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        MemberPrincipal memberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        MemberDto member = memberPrincipal.getMember();

        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail(), authentication.getAuthorities());

        String targetUrl = getTargetUrl(token);

        if (response.isCommitted()) {
            this.logger.debug(LogMessage.format("Did not redirect to %s since response already committed.", targetUrl));
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String getTargetUrl(String token) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("token", token);
        return URLUtils.createUrl(DEFAULT_REDIRECT_URL, queryParams);
    }

}
