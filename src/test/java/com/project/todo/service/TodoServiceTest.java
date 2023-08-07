package com.project.todo.service;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.dto.TodoDto;
import com.project.todo.domain.types.TODO_TYPE;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired TodoService todoService;
    @Autowired MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    void nullMember() {
        TodoDto dto = new TodoDto();
        dto.setMemberId(null);
        dto.setTodoId(1L);
        dto.setContent("hello");

        assertThrows(InvalidDataAccessApiUsageException.class, () -> todoService.saveTodo(dto));
    }

    @Test
    void noExistedMember() {
        TodoDto dto = new TodoDto();
        dto.setMemberId(1L);
        dto.setTodoId(1L);
        dto.setContent("hello");

        assertThrows(IllegalStateException.class, () -> todoService.saveTodo(dto));
    }

    @Test
    void insertTodo() {
        MemberDto testMember = new MemberDto();
        testMember.setName("test1");
        testMember.setEmail("test@naver.com");
        testMember.setPassword("1111");

        MemberDto savedMemberDto = memberService.doJoin(testMember);

        TodoDto memberAndTodoDto = new TodoDto();
        memberAndTodoDto.setMemberId(savedMemberDto.getId());
        memberAndTodoDto.setTitle("test1");
        memberAndTodoDto.setType(TODO_TYPE.COMMON);
        memberAndTodoDto.setContent("test data");

        TodoDto savedTodo = todoService.saveTodo(memberAndTodoDto);

        Assertions.assertThat(savedTodo.getTitle()).isEqualTo("test1");
        Assertions.assertThat(savedTodo.getType()).isEqualTo(TODO_TYPE.COMMON);
        Assertions.assertThat(savedTodo.getContent()).isEqualTo("test data");
        Assertions.assertThat(savedTodo.getMemberId()).isEqualTo(savedMemberDto.getId());
        Assertions.assertThat(savedTodo.getCreated()).isNotNull();
        Assertions.assertThat(savedTodo.getUpdated()).isNull();
    }

    @Test
    void updateTodo() {
        MemberDto testMember = new MemberDto();
        testMember.setName("test1");
        testMember.setEmail("test@naver.com");
        testMember.setPassword("1111");

        MemberDto savedMemberDto = memberService.doJoin(testMember);

        TodoDto memberAndTodoDto = new TodoDto();
        memberAndTodoDto.setMemberId(savedMemberDto.getId());
        memberAndTodoDto.setTitle("test1");
        memberAndTodoDto.setType(TODO_TYPE.COMMON);
        memberAndTodoDto.setContent("test data");

        TodoDto savedTodo = todoService.saveTodo(memberAndTodoDto);

        // do update
        TodoDto updateDto = new TodoDto();
        updateDto.setMemberId(savedMemberDto.getId());
        updateDto.setTodoId(savedTodo.getTodoId());
        updateDto.setTitle("test2");
        updateDto.setType(TODO_TYPE.COMMON);
        updateDto.setContent("test data2");

        TodoDto updateTodo = todoService.saveTodo(updateDto);

        // 수정한 값들이 변경 되었는지
        Assertions.assertThat(updateTodo.getTitle()).isEqualTo("test2");
        Assertions.assertThat(updateTodo.getType()).isEqualTo(TODO_TYPE.COMMON);
        Assertions.assertThat(updateTodo.getContent()).isEqualTo("test data2");
        Assertions.assertThat(updateTodo.getMemberId()).isEqualTo(savedTodo.getMemberId());
        Assertions.assertThat(updateTodo.getTodoId()).isEqualTo(savedTodo.getTodoId());
        Assertions.assertThat(savedTodo.getCreated()).isEqualTo(updateTodo.getCreated());

        em.flush();
        em.clear();

        TodoDto todo = todoService.findTodo(updateTodo.getTodoId());
        log.info("todo = {}", todo);
    }
}