package com.project.todo.domain.entity;

import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"FIRST_MEMBER_ID", "SECOND_MEMBER_ID"})})
@SQLDelete(sql = "UPDATE friend SET is_delete = false WHERE friend_id = ?")
@Where(clause = "is_delete = 'N'")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Friend extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRIEND_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIRST_MEMBER_ID")
    private Member firstMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECOND_MEMBER_ID")
    private Member secondMember;

    private Long senderId;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private FRIEND_TYPE type;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private REQUEST_STATE state;

    public static Friend createFriendRelationShip(Member sender, Member receiver, @Nullable FRIEND_TYPE friendType) {
        Assert.notNull(sender.getId(), "senderId cannot be null");
        Assert.notNull(receiver.getId(), "receiverId cannot be null");

        List<Member> ids = Arrays.asList(sender, receiver);
        ids.sort(comparingLong(Member::getId));

        Friend friend = new Friend();
        friend.firstMember = ids.get(0);
        friend.secondMember = ids.get(1);
        friend.senderId = sender.getId();
        friend.type = friendType == null ? FRIEND_TYPE.COMMON : friendType;
        friend.state = REQUEST_STATE.PENDING;

        return friend;
    }

    public Friend reapplyFriendRelationShip(Long senderId, @Nullable FRIEND_TYPE friendType) {
        this.state = REQUEST_STATE.PENDING;
        this.senderId = senderId;
        this.type = friendType == null ? FRIEND_TYPE.COMMON : friendType;

        return this;
    }

    public void accept() { this.state = REQUEST_STATE.COMPLETE; }

    public void refuse() {
        this.state = REQUEST_STATE.REFUSE;
    }

    public void remove() {
        this.state = REQUEST_STATE.DESTROY;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
