package com.project.todo.exception.advice;


import com.project.todo.domain.types.RESPONSE_CODE;
import com.project.todo.controller.response.common.ResponseResult;
import com.project.todo.exception.DuplicateEmailException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseResult<Void>> badCredentialsExceptionHandler(BadCredentialsException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.NO_MATCH_PASSWORD, "BadCredentialsException"),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseResult<Void>> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.NOT_FOUND_MEMBER, "UsernameNotFoundException"),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseResult<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.INVALID_PARAMETER, "not valid due to validation error: " + e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseResult<Void>> handleIllegalStateException(IllegalStateException e) {
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.INVALID_STATE, e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.INVALID_PARAMETER, "not valid due to validation error: " + e.getBindingResult().getFieldError().getDefaultMessage()),
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

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseResult<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("find", e);
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.ACCESS_DENIED, e.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ResponseResult<Void>> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.INVALID_PARAMETER, e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseResult<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.NOT_UNIQUE, e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ResponseResult<Void>> exHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                new ResponseResult<>(RESPONSE_CODE.EXCEPTION, "Exception"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
