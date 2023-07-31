package com.project.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {

    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String isDelete;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private LocalDateTime updated;

    @PrePersist
    void preInsert() {
        if (this.isDelete == null) this.isDelete = "N";
        if (this.created == null) this.created = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        if (this.updated == null) this.updated = LocalDateTime.now();
    }
}
