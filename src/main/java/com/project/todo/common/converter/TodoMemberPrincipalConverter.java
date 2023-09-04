package com.project.todo.common.converter;

import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.service.dto.member.MemberDto;

public class TodoMemberPrincipalConverter implements MemberPrincipalConverter {

    @Override
    public boolean supports(MemberAuthenticationFactoryForm form) {
        return form.getClientRegistration() == null;
    }

    @Override
    public MemberDto converter(MemberAuthenticationFactoryForm form) {
        return MemberDto.fromEntity(form.getMember());
    }
}
