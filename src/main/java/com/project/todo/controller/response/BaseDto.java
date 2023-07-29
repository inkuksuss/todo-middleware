package com.project.todo.controller.response;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseDto {
    private String isDelete;

    private LocalDateTime created;

    private LocalDateTime updated;

    public BaseDto(String isDelete, LocalDateTime created, LocalDateTime updated) {
        this.isDelete = isDelete;
        this.created = created;
        this.updated = updated;
    }
}
