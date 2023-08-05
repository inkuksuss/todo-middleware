//package com.project.todo.factory.dtofactory;
//
//import com.project.todo.domain.dto.CustomDto;
//import com.project.todo.domain.dto.MemberAndTodoDto;
//import com.project.todo.domain.request.AddTodoReq;
//import com.project.todo.domain.request.RequestObject;
//import com.project.todo.domain.types.TODO_TYPE;
//
//public class MemberAndTodoDtoFactory<T extends RequestObject> implements DtoFactory<T> {
//
//    @Override
//    public CustomDto createDto(T request) {
//
//        if (!AddTodoReq.class.isAssignableFrom(request.getClass())) return null;
//
//        AddTodoReq addTodoReq = (AddTodoReq) request;
//        MemberAndTodoDto dto = new MemberAndTodoDto();
//        dto.setMemberId(addTodoReq.getUserId());
//        dto.setTodoId(addTodoReq.getTodoId());
//        dto.setTodoType(TODO_TYPE.COMMON);
//        dto.setTodoTitle(addTodoReq.getTodoTitle());
//        dto.setTodoContent(addTodoReq.getContent());
//
//        return dto;
//    }
//}
