package com.project.todo.common.converter;

import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.service.dto.member.MemberDto;

import java.util.UUID;

public interface MemberPrincipalConverter {

    boolean supports(MemberAuthenticationFactoryForm form);

    MemberDto converter(MemberAuthenticationFactoryForm form);

    default String getUuid() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
