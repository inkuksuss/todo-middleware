package com.project.todo.repository.todo;

import com.project.todo.domain.entity.Todo;
import com.project.todo.domain.types.COMMON_TYPE;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.todo.domain.entity.QTodo.todo;

@Slf4j
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public TodoRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    @Transactional
    public Long deleteDynamicTodo(Long todoId, Long memberId) {

        if (todoId == null && memberId == null) {
            throw new IllegalArgumentException("todo id or member id must need");
        }

        Long deleteCount = queryFactory
                .update(todo)
                .set(todo.isDelete, COMMON_TYPE.DELETE.getState())
                .where(
                        todoIdEq(todoId),
                        memberIdEq(memberId)
                )
                .execute();

        em.flush();
        em.clear();

        List<Todo> fetch = queryFactory.selectFrom(todo).where(
                todoIdEq(todoId),
                memberIdEq(memberId)
        ).fetch();

        if (fetch.size() > 0)log.info("fetch = {}", fetch.get(0));

        log.warn("em = {}", em.getClass());

//        em.flush();
//        em.clear();

        return deleteCount;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? todo.member.id.eq(memberId) : null;
    }

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) : null;
    }
}
