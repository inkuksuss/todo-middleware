package com.project.todo.common.factory.dto;

import com.project.todo.service.dto.member.MemberDto;
import com.project.todo.domain.entity.Member;

public class MemberDtoFactory implements DtoFactory<MemberDto, Member> {

    @Override
    public MemberDto createDto(Member o) {
        return null;
    }
}
