package com.project.todo.controller.advice.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private int code; // code enum
    private String message;
}
