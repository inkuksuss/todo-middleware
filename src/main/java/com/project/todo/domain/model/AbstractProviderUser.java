package com.project.todo.domain.model;

import com.project.todo.domain.types.LOGIN_PROVIDER;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractProviderUser implements ProviderUser {

    private final OAuth2User oAuth2User;

    private final ClientRegistration clientRegistration;

    private final Map<String, Object> attributes;

    public AbstractProviderUser(Map<String, Object> attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }

    @Override
    public LOGIN_PROVIDER getProvider() {
        Optional<LOGIN_PROVIDER> findOne = Arrays.stream(LOGIN_PROVIDER.values())
                .filter(provider -> provider.name().toLowerCase().equals(clientRegistration.getRegistrationId()))
                .findAny();
        return findOne.orElseThrow(() -> { throw new IllegalStateException("제공하지 않는 서비스입니다"); });
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities().stream().collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}