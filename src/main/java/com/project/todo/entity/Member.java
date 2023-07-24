package com.project.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AttributeOverrides({
        @AttributeOverride(name="created", column = @Column(name = "MEMBER_CREATED")),
        @AttributeOverride(name="updated", column = @Column(name = "MEMBER_UPDATED"))
})
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_SEQ")
    private Long memberSeq;
    private String memberName;
    private String memberId;
    private String memberPassword;
    private String memberEmail;

    @OneToMany(mappedBy = "member")
    private List<Todo> todoList = new ArrayList<Todo>();

    public void setMemberSeq(Long memberSeq) {
        this.memberSeq = memberSeq;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }
    public void addTodo(Todo todo) {
        this.todoList.add(todo);
        if (todo.getMember() != this) todo.setMember(this);
    }
}
