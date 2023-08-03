package com.project.todo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
//@SQLDelete(sql = "UPDATE member SET is_delete = false WHERE id = ?")
@Where(clause = "is_delete = 'N'")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "email", "password"})
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Column(length = 320)
    private String email;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Todo> todoList = new ArrayList<>();

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public void addTodo(Todo todo) {
        this.todoList.add(todo);
        if (todo.getMember() != this) todo.setMember(this);
    }
}