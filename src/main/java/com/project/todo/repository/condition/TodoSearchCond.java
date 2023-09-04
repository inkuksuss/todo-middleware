package com.project.todo.repository.condition;

import com.project.todo.domain.types.RELATIONSHIP_TYPE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoSearchCond {

    private String todoTitle;

    private String isComplete;

    private RELATIONSHIP_TYPE type;
}
