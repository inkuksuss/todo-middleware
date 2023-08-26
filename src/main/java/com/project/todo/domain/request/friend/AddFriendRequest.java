package com.project.todo.domain.request.friend;

import com.project.todo.domain.types.FRIEND_TYPE;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddFriendRequest {

    @NotNull
    private Long targetId;

    private FRIEND_TYPE friendType;
}
