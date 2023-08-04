package com.project.todo.factory.dtofactory;

import com.project.todo.controller.request.AddTodoReq;
import com.project.todo.controller.request.RequestObject;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.factory.dtofactory.dto.CustomDto;
import com.project.todo.factory.dtofactory.dto.MemberAndTodoDto;

public class MemberAndTodoDtoFactory<T extends RequestObject> implements DtoFactory<T> {

    @Override
    public CustomDto createDto(T request) {

        if (!AddTodoReq.class.isAssignableFrom(request.getClass())) return null;

        AddTodoReq addTodoReq = (AddTodoReq) request;
        MemberAndTodoDto dto = new MemberAndTodoDto();
        dto.setMemberId(addTodoReq.getUserId());
        dto.setTodoId(addTodoReq.getTodoId());
        dto.setTodoType(TODO_TYPE.COMMON);
        dto.setTodoTitle(addTodoReq.getTodoTitle());
        dto.setTodoContent(addTodoReq.getContent());

        return dto;
    }
}
