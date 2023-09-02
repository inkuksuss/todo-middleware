package com.project.todo.domain.model.member;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.types.LOGIN_PROVIDER;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface MemberHolder {

    MemberDto getMember();

    void updateMember(MemberDto memberDto);

    Long getId();

    String getUsername();

    String getPassword();

    String getEmail();

    LOGIN_PROVIDER getProvider();

    Map<String, Object> getAttributes();

    List<? extends GrantedAuthority> getAuthorities();
}
