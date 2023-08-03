package com.project.todo.service;

import com.project.todo.domain.factory.dtofactory.dto.MemberAndTodoDto;
import com.project.todo.domain.factory.dtofactory.dto.MemberDto;
import com.project.todo.domain.factory.dtofactory.dto.TodoDto;
import com.project.todo.domain.types.TODO_TYPE;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class TodoServiceTest {

    @Autowired TodoService todoService;
    @Autowired MemberService memberService;

    @Test
    void nullMember() {
        MemberAndTodoDto dto = new MemberAndTodoDto();
        dto.setMemberId(null);
        dto.setTodoId(1L);
        dto.setTodoContent("hello");

        assertThrows(InvalidDataAccessApiUsageException.class, () -> todoService.saveTodo(dto));
    }

    @Test
    void noExistedMember() {
        MemberAndTodoDto dto = new MemberAndTodoDto();
        dto.setMemberId(1L);
        dto.setTodoId(1L);
        dto.setTodoContent("hello");

        assertThrows(IllegalStateException.class, () -> todoService.saveTodo(dto));
    }

    @Test
    void insertTodo() {
        MemberDto testMember = new MemberDto();
        testMember.setName("test1");
        testMember.setEmail("test@naver.com");
        testMember.setPassword("1111");

        MemberDto savedMemberDto = memberService.doJoin(testMember);

        MemberAndTodoDto memberAndTodoDto = new MemberAndTodoDto();
        memberAndTodoDto.setMemberId(savedMemberDto.getId());
        memberAndTodoDto.setTodoTitle("test1");
        memberAndTodoDto.setTodoType(TODO_TYPE.COMMON);
        memberAndTodoDto.setTodoContent("test data");

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

        MemberAndTodoDto memberAndTodoDto = new MemberAndTodoDto();
        memberAndTodoDto.setMemberId(savedMemberDto.getId());
        memberAndTodoDto.setTodoTitle("test1");
        memberAndTodoDto.setTodoType(TODO_TYPE.COMMON);
        memberAndTodoDto.setTodoContent("test data");

        TodoDto savedTodo = todoService.saveTodo(memberAndTodoDto);

        // do update
        MemberAndTodoDto updateDto = new MemberAndTodoDto();
        updateDto.setMemberId(savedMemberDto.getId());
        updateDto.setTodoId(savedTodo.getTodoId());
        updateDto.setTodoTitle("test2");
        updateDto.setTodoType(TODO_TYPE.COMMON);
        updateDto.setTodoContent("test data2");

        TodoDto updateTodo = todoService.saveTodo(updateDto);

        // 수정한 값들이 변경 되었는지
        Assertions.assertThat(updateTodo.getTitle()).isEqualTo("test2");
        Assertions.assertThat(updateTodo.getType()).isEqualTo(TODO_TYPE.COMMON);
        Assertions.assertThat(updateTodo.getContent()).isEqualTo("test data2");
        Assertions.assertThat(updateTodo.getMemberId()).isEqualTo(savedTodo.getMemberId());
        Assertions.assertThat(updateTodo.getTodoId()).isEqualTo(savedTodo.getTodoId());
        Assertions.assertThat(savedTodo.getCreated()).isEqualTo(updateTodo.getCreated());
    }
}