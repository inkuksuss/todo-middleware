package com.project.todo.service.dto.todo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoSearchDto {

    private String todoTitle;

    private String isComplete;

    private Long memberId;

    private Long requestMemberId;
}
