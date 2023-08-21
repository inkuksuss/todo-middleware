package com.project.todo.domain.dto;

import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateFriendDto {

    @NotNull(message = "modifier id cannot be null")
    private Long modifierId;

    @NotNull(message = "target id cannot be null")
    private Long targetId;

    @Nullable
    private FRIEND_TYPE friendType;

    @NotNull(message = "request type cannot be null")
    private REQUEST_STATE requestType;
}
