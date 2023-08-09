package com.project.todo.repository.member;

import com.project.todo.domain.dto.MemberSearchCond;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.entity.QMember;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.project.todo.domain.entity.QMember.*;

@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Member> findPagingMemberList(MemberSearchCond cond, Pageable pageable) {
        log.info("offset = {}", pageable.getOffset());
        log.info("pagealbe = {}", pageable);
        log.info("size = {}", pageable.getPageSize());

        List<Member> memberList = queryFactory
                .select(member)
                .from(member)
                .where(
                        nameEq(cond.getName()),
                        emailEq(cond.getEmail())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(member.count())
                .from(member)
                .where(
                        nameEq(cond.getName()),
                        emailEq(cond.getEmail())
                )
                .fetchOne();

        log.info("count = {}", count);

        return new PageImpl<>(memberList, pageable, count);
    }

    private BooleanExpression nameEq(String name) {
        return StringUtils.hasText(name) ? member.name.eq(name) : null;
    }

    private BooleanExpression emailEq(String email) {
        return StringUtils.hasText(email) ? member.email.eq(email) : null;
    }
}
