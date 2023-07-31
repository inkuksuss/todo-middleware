package com.project.todo.entity;

import com.project.todo.domain.types.TODO_TYPE;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@SQLDelete(sql = "UPDATE todo SET is_delete = false WHERE id = ?")
@Where(clause = "is_delete = 'N'")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "type", "content"})
public class Todo extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private Long id;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private TODO_TYPE type;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

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

    public void setMember(Member member) {
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
}
