package com.project.todo.domain.response;

import com.project.todo.domain.types.MEMBER_TYPE;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class MemberDetailResponse {

    private Long id;

    private String name;

    private String email;

    private MEMBER_TYPE type;

    private LocalDateTime created;

    private LocalDateTime updated;

}
