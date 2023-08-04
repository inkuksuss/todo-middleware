package com.project.todo.domain.response.common;


import lombok.Getter;

@Getter
public class ResponseResult<T> {

    private int code;

    private String message;

    private T data;

    public ResponseResult() {
    }

    public ResponseResult(RESPONSE_CODE code) {
        this.code = code.getCode();
    }

    public ResponseResult(RESPONSE_CODE code, String meesage) {
        this.code = code.getCode();
        this.message = meesage;
    }

    public ResponseResult(RESPONSE_CODE code, String meesage, T data) {
        this.code = code.getCode();
        this.message = meesage;
        this.data = data;
    }
}
