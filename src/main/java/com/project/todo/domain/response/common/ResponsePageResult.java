package com.project.todo.domain.response.common;


import com.project.todo.domain.dto.PageDto;
import com.project.todo.domain.types.RESPONSE_CODE;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponsePageResult<T> {

    private int code = 99;

    private String message;

    private long totalCount;

    private int totalPage;

    private Integer currentPage;

    private boolean hasNext;

    private List<T> data;

    public ResponsePageResult(PageDto<T> pageDto) {
        this.code = RESPONSE_CODE.SUCCESS.getCode();
        this.data = pageDto.getDataList();
        this.totalCount = pageDto.getTotalCount();
        this.totalPage = pageDto.getTotalPage();
        this.currentPage = pageDto.getCurrentPage();
        this.hasNext = pageDto.isHasNext();
    }

    @Override
    public String toString() {
        return "ResponsePageResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", hasNext=" + hasNext +
                ", data=" + data +
                '}';
    }
}
