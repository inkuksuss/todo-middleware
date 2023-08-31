package com.project.todo.common.converter;

import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.domain.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class DelegatingMemberPrincipalConverter {

    private final List<MemberPrincipalConverter> converterList;

    public DelegatingMemberPrincipalConverter() {
        List<MemberPrincipalConverter> converters = Arrays.asList(
                new TodoMemberPrincipalConverter(),
                new GoogleMemberPrincipalConverter(),
                new NaverMemberPrincipalConverter()
        );

        this.converterList = Collections.unmodifiableList(new LinkedList<>(converters));
    }

    public MemberDto convert(MemberAuthenticationFactoryForm form) {
        for (MemberPrincipalConverter converter : converterList) {
            if (!converter.supports(form)) {
                continue;
            }

            return converter.converter(form);
        }

        return null;
    }
}
