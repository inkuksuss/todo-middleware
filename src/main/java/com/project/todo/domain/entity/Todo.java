package com.project.todo.domain.entity;

import com.project.todo.domain.types.TODO_TYPE;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Entity
@Getter
@SQLDelete(sql = "UPDATE todo SET is_delete = false WHERE todo_id = ?")
@Where(clause = "is_delete = 'N'")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = "member")
public class Todo extends BaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private Long id;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private TODO_TYPE type;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String isComplete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne

    private Long creatorId;

    public Todo(Long id) {
        this.id = id;
    }

    public Todo(Long id, TODO_TYPE type, String title, String content) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public Todo(TODO_TYPE type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public void changeMember(Member member) {
        this.member = member;
        if (!member.getTodoList().contains(this)) member.getTodoList().add(this);
    }

    public void setType(TODO_TYPE type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }
}
