package com.project.todo.controller;

import com.project.todo.domain.dto.TodoDto;
import com.project.todo.domain.request.TodoDetailRequest;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/save")
    public ResponseEntity<?> addTodo(@RequestBody @Validated TodoDetailRequest request) {
        // TODO validation

        TodoDto dto = new TodoDto();
        dto.setMemberId(request.getMemberId());
        dto.setTodoId(request.getTodoId());
        dto.setType(TODO_TYPE.COMMON);
        dto.setTitle(request.getTitle());
        dto.setContent(request.getContent());

        TodoDto todoDto = todoService.saveTodo(dto);

//        return new ResponseEntity<>(new AddTodoRes(dto.getMemberId(), todoDto), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/remove/{todoId}")
//    public ResponseEntity<AddTodoRes> addTodo(@PathVariable Long todoId) {
//
//        TodoDto todoDto = todoService.saveTodo(dto);
//
//        return new ResponseEntity<>(new AddTodoRes(dto.getMemberId(), todoDto), HttpStatus.OK);
//    }
}
