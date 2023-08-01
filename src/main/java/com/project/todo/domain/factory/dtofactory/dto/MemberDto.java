package com.project.todo.domain.factory.dtofactory.dto;

import com.project.todo.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@ToString
public class MemberDto implements CustomDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<TodoDto> todoList = new ArrayList<>();

    private String isDelete;
    private LocalDateTime created;
    private LocalDateTime updated;

    public static MemberDto fromEntity(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.id = member.getId();
        memberDto.name = member.getName();
        memberDto.email = member.getEmail();
        memberDto.isDelete = member.getIsDelete();
        memberDto.created = member.getCreated();
        memberDto.updated = member.getUpdated();
        memberDto.todoList = member.getTodoList().stream()
                .map(TodoDto::fromEntity)
                .collect(Collectors.toList());

        return memberDto;
    }
}
