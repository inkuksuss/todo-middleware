package com.project.todo.domain.factory.dtofactory;

import com.project.todo.controller.request.AddTodoReq;
import com.project.todo.controller.request.RequestObejct;
import com.project.todo.domain.factory.dtofactory.dto.CustomDto;
import com.project.todo.domain.factory.dtofactory.dto.MemberAndTodoDto;
import com.project.todo.domain.types.TODO_TYPE;

public class MemberAndTodoDtoFactory<T extends RequestObejct> implements DtoFactory<T> {

    @Override
    public CustomDto createDto(T request) {
        MemberAndTodoDto dto = null;
        if (request instanceof AddTodoReq) {
            AddTodoReq addTodoReq = (AddTodoReq) request;
            dto = new MemberAndTodoDto();
            dto.setMemberId(addTodoReq.getUserId());
            dto.setTodoId(addTodoReq.getTodoId());
            dto.setTodoType(TODO_TYPE.COMMON);
            dto.setTodoTitle(addTodoReq.getTodoTitle());
            dto.setTodoContent(addTodoReq.getContent());
        }

        return dto;
    }
}
