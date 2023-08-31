package com.project.todo.common.converter;

import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.types.LOGIN_PROVIDER;
import com.project.todo.domain.types.MEMBER_TYPE;
import org.springframework.util.StringUtils;

import java.util.Map;

public class GoogleMemberPrincipalConverter implements MemberPrincipalConverter {

    @Override
    public boolean supports(MemberAuthenticationFactoryForm form) {
        return LOGIN_PROVIDER.GOOGLE.getProviderValue().equals(form.getClientRegistration().getRegistrationId());
    }

    @Override
    public MemberDto converter(MemberAuthenticationFactoryForm form) {

        Map<String, Object> attributes = form.getOAuth2User().getAttributes();

        String name = (String) attributes.get("name");
        if (!StringUtils.hasText(name)) {
            name = this.getUuid();
        }

        String email = (String) attributes.get("email");
        if (!StringUtils.hasText(email)) {
            throw new IllegalStateException("이메일 정보는 필수 입니다.");
        }

        MemberDto memberDto = new MemberDto();
        memberDto.setName(name);
        memberDto.setEmail(email);
        memberDto.setPassword(this.getUuid());
        memberDto.setType(MEMBER_TYPE.MEMBER);
        memberDto.setProvider(LOGIN_PROVIDER.GOOGLE);

        return memberDto;
    }
}
