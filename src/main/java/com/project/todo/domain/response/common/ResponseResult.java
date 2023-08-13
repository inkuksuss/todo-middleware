package com.project.todo.domain.response.common;


import com.project.todo.domain.types.RESPONSE_CODE;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseResult<T> {

    private int code = 99;

    private String message;

    private T data;

    public ResponseResult() {
        this.code = RESPONSE_CODE.SUCCESS.getCode();
    }

    public ResponseResult(T t) {
        this.code = RESPONSE_CODE.SUCCESS.getCode();
        this.data = t;
    }

    public ResponseResult(RESPONSE_CODE code, String message) {
        this.code = code.getCode();
        this.message = message;
    }

    public ResponseResult(RESPONSE_CODE code, String message, T data) {
        this.code = code.getCode();
        this.message = message;
        this.data = data;
    }
}
