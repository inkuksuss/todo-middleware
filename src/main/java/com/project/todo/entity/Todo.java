package com.project.todo.entity;

import com.project.todo.domain.types.TODO_TYPE;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@AttributeOverrides({
        @AttributeOverride(name="created", column = @Column(name = "TODO_CREATED")),
        @AttributeOverride(name="updated", column = @Column(name = "TODO_UPDATED"))
})
@Getter
public class Todo extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_SEQ")
    private Long todoSeq;
    private TODO_TYPE todoType;
    private String todoName;
    private String todoContent;
    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    public void setTodoSeq(Long todoSeq) {
        this.todoSeq = todoSeq;
    }

    public void setTodoType(TODO_TYPE todoType) {
        this.todoType = todoType;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public void setTodoContent(String todoContent) {
        this.todoContent = todoContent;
    }

    public void setMember(Member member) {
        this.member = member;
        if (!member.getTodoList().contains(this)) member.getTodoList().add(this);
    }
}
