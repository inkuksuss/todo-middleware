package com.project.todo.domain.condition;

import com.project.todo.domain.types.REQUEST_STATE;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class FriendSearchCond extends AbstractPageableDto {

    @NotNull(message = "target id cannot be null")
    private Long targetId;

    private Long senderId;

    private String friendName;

    private String friendEmail;

    private REQUEST_STATE requestState;
}
