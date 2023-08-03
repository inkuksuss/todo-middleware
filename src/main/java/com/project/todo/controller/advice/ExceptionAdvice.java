package com.project.todo.controller.advice;


import com.project.todo.controller.advice.handler.ErrorResult;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.exception.NoMatchPasswordException;
import com.project.todo.exception.NotFoundMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DuplicateEmailException.class)
    public ErrorResult duplicateEmailHandler(DuplicateEmailException e) {
        log.error("[ex handler] ex", e);
        return new ErrorResult(1, "DuplicateEmailException");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoMatchPasswordException.class)
    public ErrorResult noMatchPasswordHandler(NoMatchPasswordException e) {
        log.error("[ex handler] ex", e);
        return new ErrorResult(1, "NoMatchPasswordException");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotFoundMemberException.class)
    public ErrorResult notFoundMemberHandler(NotFoundMemberException e) {
        log.error("[ex handler] ex", e);
        return new ErrorResult(1, "NotFoundMemberException");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ErrorResult(1, "Exception");
    }
}
