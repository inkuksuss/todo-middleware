package com.project.todo.domain.request.friend;

import com.project.todo.domain.request.PageRequest;
import com.project.todo.domain.types.REQUEST_STATE;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class FriendSearchRequest extends PageRequest {

    @Nullable
    private Long senderId;

    @Nullable
    private String friendName;

    @Nullable
    private String friendEmail;

    @Nullable
    private REQUEST_STATE requestState;
}
