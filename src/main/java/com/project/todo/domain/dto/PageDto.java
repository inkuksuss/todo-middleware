package com.project.todo.domain.dto;

import lombok.Getter;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageDto<T> {

    private long totalCount;

    private int totalPage;

    private Integer currentPage;

    private boolean hasNext;

    private List<T> dataList = new ArrayList<>();

    public PageDto(long totalCount, int totalPage, boolean hasNext, List<T> dataList) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.hasNext = hasNext;
        this.dataList = dataList;
    }

    public PageDto(long totalCount, int totalPage, Integer currentPage, boolean hasNext, List<T> dataList) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.hasNext = hasNext;
        this.dataList = dataList;
    }
}
