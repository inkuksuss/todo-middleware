package com.project.todo.domain.request.friend;

import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateFriendRequest {

    @NotEmpty
    private REQUEST_STATE requestState;
}
