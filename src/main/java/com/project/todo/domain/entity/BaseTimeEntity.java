package com.project.todo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@ToString
public abstract class BaseTimeEntity {

    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String isDelete;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updated;

    @PrePersist
    void preInsert() {
        if (this.isDelete == null) this.isDelete = "N";
    }
}
