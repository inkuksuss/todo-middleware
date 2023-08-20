package com.project.todo.repository.friend;

import com.project.todo.domain.dto.FriendSimpleDynamicDto;
import com.project.todo.domain.entity.Friend;
import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.project.todo.domain.entity.QFriend.*;

@Validated
@Repository
public class FriendRepositoryImpl implements FriendRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public FriendRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Friend findSimpleDynamicFriend(@Valid FriendSimpleDynamicDto dynamicDto) {
        return queryFactory
                .selectFrom(friend)
                .where(
                        firstIdEq(dynamicDto.getFirstMemberId()),
                        secondIdEq(dynamicDto.getSecondMemberId()),
                        senderIdEq(dynamicDto.getSenderId()),
                        requestStateEq(dynamicDto.getRequestType()),
                        friendTypeEq(dynamicDto.getFriendType())
                )
                .fetchOne();
    }

    private BooleanExpression firstIdEq(Long id) {
        return id != null ? friend.firstMember.id.eq(id) : null;
    }

    private BooleanExpression secondIdEq(Long id) {
        return id != null ? friend.secondMember.id.eq(id) : null;
    }

    private BooleanExpression senderIdEq(Long id) {
        return id != null ? friend.senderId.eq(id) : null;
    }

    private BooleanExpression requestStateEq(REQUEST_STATE requestType) {
        return requestType != null ? friend.state.eq(requestType) : null;
    }

    private BooleanExpression friendTypeEq(FRIEND_TYPE friendType) {
        return friendType != null ? friend.type.eq(friendType) : null;
    }
}
