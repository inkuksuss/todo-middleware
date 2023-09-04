package com.project.todo.service.dto.friend;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
public class FriendDetailDto {

    private Long friendId;

    private Long memberId;

    private String memberName;

    private String memberEmail;

    private LocalDateTime updated;
}
