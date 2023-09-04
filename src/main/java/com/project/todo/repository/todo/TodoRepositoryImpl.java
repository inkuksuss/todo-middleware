package com.project.todo.repository.todo;

import com.project.todo.repository.condition.TodoSearchCond;
import com.project.todo.service.dto.PageDto;
import com.project.todo.service.dto.todo.TodoDto;
import com.project.todo.domain.types.COMMON_TYPE;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.project.todo.domain.entity.QMember.member;
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

        return deleteCount;
    }

    @Override
    public PageDto<TodoDto> findTodoListOfMember(TodoSearchCond cond, Pageable pageable) {
        queryFactory
                .select(todo)
                .from(todo)
                .join(todo.member, member)
                .where()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(todoSort(pageable))
                .fetch();
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? todo.member.id.eq(memberId) : null;
    }

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) : null;
    }

    private BooleanExpression todoTitleEq(String title) {
        return StringUtils.hasText(title) ? todo.title.eq(title) : null;
    }

    private BooleanExpression todoIsCompleteEq(String isComplete) {
        return StringUtils.hasText(isComplete) ? todo.isComplete.eq(isComplete) : null;
    }

    private OrderSpecifier<?> todoSort(Pageable page) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()) {
                    case "updated" -> {
                        return new OrderSpecifier(direction, todo.updated);
                    }
                    case "created" -> {
                        return new OrderSpecifier(direction, todo.created);
                    }
                }
            }
        }
        return null;
    }
}
