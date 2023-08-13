package com.project.todo.domain.response;

import com.project.todo.domain.types.MEMBER_TYPE;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class MemberDetailResponse {

    private Long id;

    private String name;

    private String email;

    private MEMBER_TYPE type;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String token;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(MEMBER_TYPE type) {
        this.type = type;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void addToken(String token) {
        this.token = token;
    }
}
