package com.project.todo.domain.dto;

import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.LOGIN_PROVIDER;
import com.project.todo.domain.types.MEMBER_TYPE;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class MemberDto {

    private Long id;

    private String name;

    @Email
    private String email;

    private String password;

    private MEMBER_TYPE type;

    private LOGIN_PROVIDER provider;

    private List<TodoDto> todoList = new ArrayList<>();

    private String isDelete;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String token;

    public static MemberDto fromEntity(Member member) {
        Assert.notNull(member.getId(), "member id is not null");

        MemberDto memberDto = new MemberDto();
        memberDto.id = member.getId();
        memberDto.name = member.getName();
        memberDto.email = member.getEmail();
        memberDto.type = member.getType();
        memberDto.provider = member.getProvider();
        memberDto.isDelete = member.getIsDelete();
        memberDto.created = member.getCreated();
        memberDto.updated = member.getUpdated();

        return memberDto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(MEMBER_TYPE type) {
        this.type = type;
    }

    public void setTodoList(List<TodoDto> todoList) {
        this.todoList = todoList;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setProvider(LOGIN_PROVIDER provider) { this.provider = provider; }

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
