package com.project.todo.domain.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageDto<T> {

    private long totalCount;

    private int totalPage;

    private boolean hasNext;

    private List<T> dataList = new ArrayList<>();

    public PageDto(long totalCount, int totalPage, boolean hasNext, List<T> dataList) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.hasNext = hasNext;
        this.dataList = dataList;
    }
}
