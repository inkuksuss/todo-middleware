package com.project.todo.domain.condition;

import com.project.todo.utils.constants.PageConst;

public abstract class AbstractPageableDto implements PageableDto {

    private Integer page;

    private Integer size;

    @Override
    public final int getPage() {
        return this.page == null || this.page < 0 ? PageConst.DEFAULT_PAGE : this.page;
    }

    @Override
    public final int getSize() {
        return this.size == null || this.size < 1 ? PageConst.DEFAULT_PAGE_SIZE : this.size;
    }

    public void setPage(Integer page) {
        this.page = page == null || page < 0 ? PageConst.DEFAULT_PAGE : page;
    }

    public void setSize(Integer size) {
        this.size = size == null || size < 1 ? PageConst.DEFAULT_PAGE_SIZE : size;
    }
}
