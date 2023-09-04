package com.project.todo.service.dto.friend;

import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
public class UpdateFriendDto {

    @NotNull(message = "modifier id cannot be null")
    private Long modifierId;

    @NotNull(message = "friend id cannot be null")
    private Long friendId;

    @NotNull(message = "request type cannot be null")
    private REQUEST_STATE requestType;
}
