package com.project.todo.factory.oauth;

import com.project.todo.domain.model.GoogleUser;
import com.project.todo.domain.model.NaverUser;
import com.project.todo.domain.model.ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OauthUserFactory {

    private final ClientRegistration clientRegistration;

    public OauthUserFactory(ClientRegistration clientRegistration) {
        this.clientRegistration = clientRegistration;
    }

    public ProviderUser createUser(OAuth2User oAuth2User) {
        return switch (this.clientRegistration.getRegistrationId()) {
            case "google" -> new GoogleUser(oAuth2User, this.clientRegistration);
            case "naver" -> new NaverUser(oAuth2User, this.clientRegistration);
            default -> throw new IllegalArgumentException("지원하지 않는 Provider 타입입니다.");
        };

    }
}
