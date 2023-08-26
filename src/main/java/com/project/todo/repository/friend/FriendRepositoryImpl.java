package com.project.todo.repository.friend;

import com.project.todo.domain.condition.FriendSearchCond;
import com.project.todo.domain.dto.FriendDetailDto;
import com.project.todo.domain.dto.FriendSimpleDynamicDto;
import com.project.todo.domain.entity.Friend;
import com.project.todo.domain.entity.QMember;
import com.project.todo.domain.types.FRIEND_TYPE;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.domain.types.REQUEST_STATE;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;


import java.util.List;
import java.util.Optional;

import static com.project.todo.domain.entity.QFriend.*;
import static com.project.todo.domain.entity.QMember.*;


// TODO @valid 레포지토리에 사용시 프록시 문제
@Slf4j
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
    public Optional<Friend> findSimpleDynamicFriend(FriendSimpleDynamicDto dynamicDto) {
        if (dynamicDto.getFirstMemberId() == null) {
            throw new IllegalArgumentException();
        }

        if (dynamicDto.getSecondMemberId() == null) {
            throw new IllegalArgumentException();
        }

        return Optional.ofNullable(
                queryFactory
                    .selectFrom(friend)
                    .where(
                        firstIdEq(dynamicDto.getFirstMemberId()),
                        secondIdEq(dynamicDto.getSecondMemberId()),
                        senderIdEq(dynamicDto.getSenderId()),
                        requestStateEq(dynamicDto.getRequestType()),
                        friendTypeEq(dynamicDto.getFriendType())
                    )
                    .fetchOne());
    }

    @Override
    public Page<FriendDetailDto> findSearchDynamicFriend(FriendSearchCond searchCond, Pageable pageable) {
        QMember firstMember = new QMember("firstMember");
        QMember secondMember = new QMember("secondMember");

        List<FriendDetailDto> dtoList = queryFactory
                .select(Projections.bean(
                        FriendDetailDto.class,
                        friend.id.as("friendId"),
                        (friend.senderId.equals(friend.firstMember.id) ? friend.secondMember.id : friend.firstMember.id).as("memberId"),
                        (friend.senderId.equals(friend.firstMember.id) ? friend.secondMember.name : friend.firstMember.name).as("memberName"),
                        (friend.senderId.equals(friend.firstMember.id) ? friend.secondMember.email : friend.firstMember.email).as("memberEmail"),
                        friend.updated.as("updated")
                ))
                .from(friend)
                .join(friend.firstMember, firstMember)
                .join(friend.secondMember, secondMember)
                .where(
                        firstIdEq(searchCond.getTargetId()).or(secondIdEq(searchCond.getTargetId())),
                        senderIdEq(searchCond.getSenderId()),
                        requestStateEq(searchCond.getRequestState()),
                        memberNameEq(searchCond.getFriendName()),
                        memberEmailEq(searchCond.getFriendEmail())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(friend.count())
                .from(friend)
                .join(friend.firstMember, firstMember)
                .join(friend.secondMember, secondMember)
                .where(
                        firstIdEq(searchCond.getTargetId()).or(secondIdEq(searchCond.getTargetId())),
                        senderIdEq(searchCond.getSenderId()),
                        requestStateEq(searchCond.getRequestState()),
                        memberNameEq(searchCond.getFriendName()),
                        memberEmailEq(searchCond.getFriendEmail())
                )
                .fetchOne();
        return new PageImpl<>(dtoList, pageable, count == null ? 0 : count);
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

    private BooleanExpression memberNameEq(String memberName) {
        return StringUtils.hasText(memberName) ? member.name.eq(memberName) : null;
    }

    private BooleanExpression memberEmailEq(String memberEmail) {
        return StringUtils.hasText(memberEmail) ? member.email.eq(memberEmail) : null;
    }
}
