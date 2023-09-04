package com.project.todo.service.dto.friend;

import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendSimpleDynamicDto {

    @NotNull
    private Long firstMemberId;

    @NotNull
    private Long secondMemberId;

    private Long senderId;

    private REQUEST_STATE requestType;

    private FRIEND_TYPE friendType;
}
