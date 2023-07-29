package com.project.todo.controller.response;

import com.project.todo.entity.Member;
import com.project.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MemberDto extends BaseDto {

    private Long id;
    private String name;
    private String email;
    private List<Todo> todoList;

    private MemberDto(Long id, String name, String email, List<Todo> todoList, String isDelete, LocalDateTime created, LocalDateTime updated) {
        super(isDelete, created, updated);
        this.id = id;
        this.name = name;
        this.email = email;
        this.todoList = todoList;
    }

    public static MemberDto fromEntity(Member member) {
        return new MemberDto(
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
