package com.project.todo.domain.model;

import com.project.todo.domain.types.LOGIN_PROVIDER;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ProviderUser {

    String getId();

    String getUsername();

    String getPassword();

    String getEmail();

    LOGIN_PROVIDER getProvider();

    List<? extends GrantedAuthority> getAuthorities();

    Map<String, Object> getAttributes();
}
