package com.project.todo.domain.response.common;


import com.project.todo.domain.dto.PageDto;
import com.project.todo.domain.types.RESPONSE_CODE;
import lombok.Getter;

@Getter
public class ResponseResult<T> {

    private int code;

    private String message;

    private T data;

    public ResponseResult() {
        this.code = RESPONSE_CODE.SUCCESS.getCode();
    }

    public ResponseResult(T t) {
        this.code = RESPONSE_CODE.SUCCESS.getCode();
        this.data = t;
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

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
