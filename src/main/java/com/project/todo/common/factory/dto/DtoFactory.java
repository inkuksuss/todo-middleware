package com.project.todo.common.factory.dto;

public interface DtoFactory<T, S> {

    T createDto(S s);
}
