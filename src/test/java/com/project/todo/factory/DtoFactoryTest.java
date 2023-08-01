package com.project.todo.factory;

import com.project.todo.controller.request.AddTodoReq;
import com.project.todo.domain.factory.dtofactory.DtoFactory;
import com.project.todo.domain.factory.dtofactory.MemberAndTodoDtoFactory;
import com.project.todo.domain.factory.dtofactory.dto.CustomDto;
import com.project.todo.domain.factory.dtofactory.dto.MemberAndTodoDto;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DtoFactoryTest {

    @Test
    void getMemberAndToDto() {
        AddTodoReq addTodoReq = new AddTodoReq(1L, 1L, "title", "content");

        DtoFactory<AddTodoReq> factory = new MemberAndTodoDtoFactory<>();
        MemberAndTodoDto dto = (MemberAndTodoDto) factory.createDto(addTodoReq);

        Assertions.assertThat(dto.getMemberId()).isEqualTo(1L);
        Assertions.assertThat(dto.getTodoId()).isEqualTo(1L);
        Assertions.assertThat(dto.getTodoTitle()).isEqualTo("title");
        Assertions.assertThat(dto.getTodoContent()).isEqualTo("content");
        Assertions.assertThat(dto.getTodoType()).isEqualTo(TODO_TYPE.COMMON);
    }
}