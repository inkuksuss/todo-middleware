package com.project.todo.domain.response;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseRes {
    private String isDelete;

    private LocalDateTime created;

    private LocalDateTime updated;

    public BaseRes(String isDelete, LocalDateTime created, LocalDateTime updated) {
        this.isDelete = isDelete;
        this.created = created;
        this.updated = updated;
    }
}
