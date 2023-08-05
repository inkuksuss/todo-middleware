package com.project.todo.controller;

import com.project.todo.domain.dto.MemberAndTodoDto;
import com.project.todo.domain.dto.TodoDto;
import com.project.todo.domain.request.TodoDetailRequest;
import com.project.todo.domain.response.todo.AddTodoRes;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/save")
    public ResponseEntity<AddTodoRes> addTodo(@RequestBody TodoDetailRequest request) {
        // TODO validation

        MemberAndTodoDto dto = new MemberAndTodoDto();
        dto.setMemberId(request.memberId());
        dto.setTodoId(request.todoId());
        dto.setTodoType(TODO_TYPE.COMMON);
        dto.setTodoTitle(request.title());
        dto.setTodoContent(request.content());

        TodoDto todoDto = todoService.saveTodo(dto);

        return new ResponseEntity<>(new AddTodoRes(dto.getMemberId(), todoDto), HttpStatus.OK);
    }

//    @PostMapping("/remove/{todoId}")
//    public ResponseEntity<AddTodoRes> addTodo(@PathVariable Long todoId) {
//
//        TodoDto todoDto = todoService.saveTodo(dto);
//
//        return new ResponseEntity<>(new AddTodoRes(dto.getMemberId(), todoDto), HttpStatus.OK);
//    }
}
