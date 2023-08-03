package com.project.todo.domain.factory.dtofactory.dto;

import com.project.todo.domain.types.TODO_TYPE;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberAndTodoDto implements CustomDto {

    private Long memberId;
    private String memberName;
    private String memberEmail;
    private String memberPassword;

    private Long todoId;
    private TODO_TYPE todoType;
    private String todoTitle;
    private String todoContent;
}