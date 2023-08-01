package com.project.todo.controller.response.member;

import com.project.todo.controller.response.BaseRes;
import com.project.todo.domain.factory.dtofactory.dto.MemberDto;
import com.project.todo.domain.factory.dtofactory.dto.TodoDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GetDefaultMemberRes extends BaseRes {

    private Long id;
    private String name;
    private String email;
    private List<TodoDto> todoList = new ArrayList<>();

    private GetDefaultMemberRes(Long id, String name, String email, List<TodoDto> tList, String isDelete, LocalDateTime created, LocalDateTime updated) {
        super(isDelete, created, updated);
        this.id = id;
        this.name = name;
        this.email = email;
        this.todoList = tList;
    }

    public static GetDefaultMemberRes fromDto(MemberDto member) {
        return new GetDefaultMemberRes(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getTodoList(),
                member.getIsDelete(),
                member.getCreated(),
                member.getUpdated()
        );
    }
}
