package com.project.todo.controller;

import com.project.todo.aop.annotation.Login;
import com.project.todo.config.argument_resolver.annotation.LoginId;
import com.project.todo.domain.dto.TodoDto;
import com.project.todo.domain.request.AddTodoRequest;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.domain.types.RESPONSE_CODE;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    /**
    * @param TODO_TYPE type (required = false)
    * @param string title
     *               
    * @param String content
    *
    * @return Long todoId
    *
    * @throws UsernameNotFoundException
    * @throws NoSuchElementException
    */
    @PostMapping("/save")
    public ResponseEntity<ResponseResult<Long>> addTodo(
            @RequestBody @Validated AddTodoRequest request,
            @LoginId Long memberId
    ) {
        TodoDto dto = new TodoDto();
        dto.setMemberId(memberId);
        dto.setTodoId(request.getTodoId());
        dto.setType(TODO_TYPE.COMMON);
        dto.setTitle(request.getTitle());
        dto.setContent(request.getContent());

        TodoDto todoDto = todoService.saveTodo(dto);

        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.SUCCESS, null, todoDto.getTodoId()),
                HttpStatus.OK
        );
    }

    /**
     * @param Long todoId (required = false)
     * @param TODO_TYPE type
     * @param String title
     * @param String content
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseResult<Long>> updateTodo(
            @RequestBody @Validated AddTodoRequest request,
            @LoginId Long memberId
    ) {
        TodoDto dto = new TodoDto();
        dto.setMemberId(memberId);
        dto.setTodoId(request.getTodoId());
        dto.setType(TODO_TYPE.COMMON);
        dto.setTitle(request.getTitle());
        dto.setContent(request.getContent());

        TodoDto todoDto = todoService.saveTodo(dto);

        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.SUCCESS, null, todoDto.getTodoId()),
                HttpStatus.OK
        );
    }


    /**
     *
     * @param todoId
     * @param memberId
     * @return
     */
    @DeleteMapping("/{todoId}")
    public ResponseEntity<ResponseResult<Void>> addTodo(
            @PathVariable Long todoId,
            @LoginId Long memberId
    ) {

        if (todoId == null) {
            throw new IllegalArgumentException("todo id can not be null");
        }

        if (memberId == null) {
            throw new IllegalArgumentException("member id can not be null");
        }

        todoService.removeOneTodo(memberId, todoId);

        return new ResponseEntity<>(new ResponseResult<>(), HttpStatus.OK);
    }
}
