package com.project.todo.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {

    private char isDelete;
    private LocalDateTime created;
    private LocalDateTime updated;
}
