package com.project.todo.controller;

import com.project.todo.controller.request.AddTodoReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {

    @PostMapping("/add")
    public ResponseEntity<?> addTodo(@RequestBody AddTodoReq addTodoReq) {
      log.info("userId ={}", addTodoReq.toString());

      return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/remove")
//    public ResponseEntity<?> removeTodo() {
//
//    }

}
