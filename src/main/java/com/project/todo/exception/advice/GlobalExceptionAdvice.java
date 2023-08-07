package com.project.todo.exception.advice;


import com.project.todo.domain.response.common.RESPONSE_CODE;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.exception.NoMatchPasswordException;
import com.project.todo.exception.NotFoundMemberException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ResponseResult<Void>> duplicateEmailHandler(DuplicateEmailException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(new ResponseResult<>(RESPONSE_CODE.DUPLICATE_EMAIL, "DuplicateEmailException"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoMatchPasswordException.class)
    public ResponseEntity<ResponseResult<Void>> noMatchPasswordHandler(NoMatchPasswordException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(new ResponseResult<>(RESPONSE_CODE.NO_MATCH_PASSWORD, "NoMatchPasswordException"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ResponseResult<Void>> notFoundMemberHandler(NotFoundMemberException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(new ResponseResult<>(RESPONSE_CODE.NOT_FOUND_MEMBER, "NotFoundMemberException"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ResponseResult<Void>> exHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(new ResponseResult<>(RESPONSE_CODE.EXCEPTION, "Exception"), HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(" find one", e);
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
