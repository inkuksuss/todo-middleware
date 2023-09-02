package com.project.todo.domain.model.member;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.types.LOGIN_PROVIDER;
import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.*;

public abstract class AbstractMemberHolder implements MemberHolder {

    private MemberDto member;

    protected final Map<String, Object> attributes;

    protected List<? extends GrantedAuthority> authorities;

    public AbstractMemberHolder(MemberDto member, Collection<? extends GrantedAuthority> authorities, @Nullable Map<String, Object> attributes) {
        Assert.notNull(member, "member cannot be null");
        Assert.notNull(authorities, "authorities cannot be null");
        this.member = member;
        this.authorities = authorities.stream().toList();
        this.attributes = attributes;
    }

    @Override
    public MemberDto getMember() {
        return this.member;
    }

    @Override
    public void updateMember(MemberDto memberDto) {
        this.member = memberDto;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public Long getId() {
        return member.getId();
    }

    @Override
    public String getUsername() {
        return this.member.getName();
    }

    @Override
    public String getEmail() {
        return member.getEmail();
    }

    @Override
    public LOGIN_PROVIDER getProvider() {
        return member.getProvider();
    }

    abstract public Map<String, Object> getAttributes();

    abstract public List<? extends GrantedAuthority> getAuthorities();
}
