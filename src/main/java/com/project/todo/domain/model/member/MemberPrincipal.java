package com.project.todo.domain.model.member;

import com.project.todo.domain.dto.MemberDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public record MemberPrincipal(MemberHolder memberHolder) implements UserDetails, OAuth2User, OidcUser {

    public MemberDto getMember() { return memberHolder.getMember(); }

    @Override
    public String getName() {
        return memberHolder.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return memberHolder.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return memberHolder.getAuthorities();
    }

    @Override
    public String getPassword() {
        return memberHolder.getPassword();
    }

    @Override
    public String getUsername() {
        return memberHolder.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }
}
