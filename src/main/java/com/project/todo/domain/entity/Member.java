package com.project.todo.domain.entity;

import com.project.todo.domain.types.LOGIN_PROVIDER;
import com.project.todo.domain.types.MEMBER_TYPE;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE member SET is_delete = 'Y' WHERE member_id = ?")
@Where(clause = "is_delete = 'N'")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = "todoList")
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Column(length = 320)
    private String email;

    private String password;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private MEMBER_TYPE type;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private LOGIN_PROVIDER provider;

    @OneToMany(mappedBy = "member")
    private List<Todo> todoList = new ArrayList<>();

    public Member(String name, String email, String password, MEMBER_TYPE type) {
        this(name, email, password, type, LOGIN_PROVIDER.TODO);
    }

    public Member(String name, String email, String password, MEMBER_TYPE type, LOGIN_PROVIDER provider) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.provider = provider;
    }

    public void forceChangeId(Long id) {
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

    public void setType(MEMBER_TYPE type) { this.type = type; }

    public void setProvider(LOGIN_PROVIDER provider) {
        this.provider = provider;
    }

    public void addTodo(Todo todo) {
        this.todoList.add(todo);
        if (todo.getMember() != this) todo.changeMember(this);
    }
}
