package com.project.todo.domain.model.member;

import com.project.todo.service.dto.member.MemberDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TodoMember extends AbstractMemberHolder {

    public TodoMember(MemberDto member, Collection<? extends GrantedAuthority> authorities) {
        super(member, authorities, null);
    }

    @Override
    public Map<String, Object> getAttributes() { return this.attributes; }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
