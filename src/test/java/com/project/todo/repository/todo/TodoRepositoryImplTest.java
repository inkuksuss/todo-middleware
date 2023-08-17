package com.project.todo.repository.todo;

import com.project.todo.domain.entity.Member;
import com.project.todo.domain.entity.Todo;
import com.project.todo.domain.types.COMMON_TYPE;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.glassfish.jaxb.core.v2.TODO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
class TodoRepositoryImplTest {


    @Autowired
    TodoRepository todoRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    @DisplayName("dynamic delete params")
//    @Commit
    void dynamicDelete() {
        // given
        Member member1 = memberRepository.save(new Member("test1", "test1@naver.com", "1111", MEMBER_TYPE.MEMBER));
        Member member2 = memberRepository.save(new Member("test2", "test2@naver.com", "1111", MEMBER_TYPE.MEMBER));
        Member member3 = memberRepository.save(new Member("test3", "test3@naver.com", "1111", MEMBER_TYPE.MEMBER));
        Member member4 = memberRepository.save(new Member("test4", "test4@naver.com", "1111", MEMBER_TYPE.MEMBER));


        Todo todo1 = new Todo(TODO_TYPE.COMMON, "1", "1");
        todo1.setMember(member1);

        Todo todo2 = new Todo(TODO_TYPE.COMMON, "2", "2");
        todo2.setMember(member1);

        Todo todo3 = new Todo(TODO_TYPE.COMMON, "3", "3");
        todo3.setMember(member2);

        Todo todo4 = new Todo(TODO_TYPE.COMMON, "4", "4");
        todo4.setMember(member2);

        Todo save1 = todoRepository.save(todo1);
        Todo save2 = todoRepository.save(todo2);
        Todo save3 = todoRepository.save(todo3);
        Todo save4 = todoRepository.save(todo4);


        // when
//        Long result1 = todoRepository.deleteDynamicTodo(save1.getId(), member2.getId());
//        Long result2 = todoRepository.deleteDynamicTodo(save1.getId(), member1.getId());
        Long result3 = todoRepository.deleteDynamicTodo(null, member2.getId());
        // then
//        assertThat(result1).isEqualTo(0);
//        assertThat(result2).isEqualTo(1);
        assertThat(result3).isEqualTo(2);
//        log.info("before = {}", em.contains(save3));
//
//        Todo merge = em.merge(save3);
//        Optional<Todo> deleteOne = todoRepository.findById(save3.getId());
//
//        log.info("after = {}", em.contains(save3));
//
//        log.info("sav3 = {}", deleteOne.get());
//        assertThat(byId.get().getIsDelete()).isEqualTo(COMMON_TYPE.DELETE.getState());

//        assertThatThrownBy(() -> todoRepository.deleteDynamicTodo(null, null))
//                .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

}