package com.project.todo.common.factory.authentication;

import com.project.todo.common.converter.DelegatingMemberPrincipalConverter;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.model.member.GoogleMember;
import com.project.todo.domain.model.member.NaverMember;
import com.project.todo.domain.model.member.MemberPrincipal;
import com.project.todo.domain.model.member.TodoMember;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class MemberAuthenticationFactory {

    public MemberPrincipal createMemberPrincipal(MemberAuthenticationFactoryForm form) {
        MemberDto member = new DelegatingMemberPrincipalConverter().convert(form);

        if (form.getClientRegistration() == null) {
            return new TodoMember(member, List.of(new SimpleGrantedAuthority(member.getType().getRole())));
        }

        return switch (form.getClientRegistration().getRegistrationId()) {
            case "google" -> new GoogleMember(member, form.getOAuth2User().getAuthorities(), form.getOAuth2User().getAttributes());
            case "naver" -> new NaverMember(member, form.getOAuth2User().getAuthorities(), form.getOAuth2User().getAttributes());
            default -> throw new IllegalArgumentException("지원하지 않는 Provider 타입입니다.");
        };
    }
}
