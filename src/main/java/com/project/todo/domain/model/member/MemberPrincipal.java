package com.project.todo.domain.model.member;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.LOGIN_PROVIDER;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface MemberPrincipal {

    MemberDto getMember();

    Long getId();

    String getUsername();

    String getPassword();

    String getEmail();

    LOGIN_PROVIDER getProvider();

    Map<String, Object> getAttributes();

    List<? extends GrantedAuthority> getAuthorities();
}
