package com.project.todo.exception.advice;


import com.project.todo.domain.types.RESPONSE_CODE;
import com.project.todo.domain.response.common.ResponseResult;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.exception.NoMatchPasswordException;
import com.project.todo.exception.NotFoundMemberException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.DUPLICATE_EMAIL, "DuplicateEmailException")
                , HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoMatchPasswordException.class)
    public ResponseEntity<ResponseResult<Void>> noMatchPasswordHandler(NoMatchPasswordException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.NO_MATCH_PASSWORD, "NoMatchPasswordException"),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ResponseResult<Void>> notFoundMemberHandler(NotFoundMemberException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.NOT_FOUND_MEMBER, "NotFoundMemberException"),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("find on", e);
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.INVALID_PARAMETER, "not valid due to validation error: " + e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseResult<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.INVALID_PARAMETER, "not valid due to validation error: " + e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ResponseResult<Void>> exHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.EXCEPTION, "Exception"),
                HttpStatus.OK
        );
    }
}
