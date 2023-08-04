package com.project.todo.exception.advice;


import com.project.todo.domain.response.common.RESPONSE_CODE;
import com.project.todo.domain.response.common.ResponseResult;
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
    public ResponseResult<Void> duplicateEmailHandler(DuplicateEmailException e) {
        log.error("[ex handler] ex", e);
        return new ResponseResult<>(RESPONSE_CODE.DUPLICATE_EMAIL, "DuplicateEmailException");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoMatchPasswordException.class)
    public ResponseResult<Void> noMatchPasswordHandler(NoMatchPasswordException e) {
        log.error("[ex handler] ex", e);
        return new ResponseResult<>(RESPONSE_CODE.NO_MATCH_PASSWORD, "NoMatchPasswordException");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseResult<Void> notFoundMemberHandler(NotFoundMemberException e) {
        log.error("[ex handler] ex", e);
        return new ResponseResult<>(RESPONSE_CODE.NOT_FOUND_MEMBER, "NotFoundMemberException");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseResult<Void> exHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseResult<>(RESPONSE_CODE.EXCEPTION, "Exception");
    }
}
