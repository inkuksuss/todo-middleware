package com.project.todo.controller;

import com.project.todo.config.argument_resolver.annotation.LoginId;
import com.project.todo.domain.dto.TodoDto;
import com.project.todo.domain.request.todo.AddTodoRequest;
import com.project.todo.domain.request.todo.UpdateTodoRequest;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.domain.types.RESPONSE_CODE;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
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
    * @param request the request form for add todo
     *
    * @return Long todoId
     *
    * @throws UsernameNotFoundException when can not find member
    * @throws NoSuchElementException
    */
    @PostMapping("/save")
    public ResponseEntity<ResponseResult<Long>> addTodo(
            @RequestBody @Validated AddTodoRequest request,
            @LoginId Long memberId
    ) {
        TodoDto dto = new TodoDto();
        dto.setMemberId(memberId);
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
     * @param request the request form for update todo
     *
     * @return void
     *
     * @throws NoSuchElementException when cannot find todo updated
     * @throws IllegalArgumentException when memberId is null
     * @throws UsernameNotFoundException when cannot find memberId which is todo owner
     */
    @PatchMapping("/{todoId}")
    public ResponseEntity<ResponseResult<Long>> updateTodo(
            @RequestBody @Validated UpdateTodoRequest request,
            @PathVariable Long todoId,
            @LoginId Long memberId
    ) {
        TodoDto dto = new TodoDto();
        dto.setMemberId(memberId);
        dto.setTodoId(todoId);
        dto.setType(TODO_TYPE.COMMON);

        if (StringUtils.hasText(request.getTitle())) {
            dto.setTitle(request.getTitle());
        }

        if (StringUtils.hasText(request.getContent())) {
            dto.setContent(request.getContent());
        }

        todoService.updateTodo(dto);

        return new ResponseEntity<>(new ResponseResult<>(), HttpStatus.OK);
    }


    /**
     *
     * @param todoId deleted todo id
     * @param memberId deleted todo owner id
     *
     * @return void
     *
     * @throws NoSuchElementException if not found todo to delete
     */
    @DeleteMapping("/{todoId}")
    public ResponseEntity<ResponseResult<Void>> deleteTodo(
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
