package com.project.todo.factory.dto;

public interface DtoFactory<T, S> {

    T createDto(S s);
}
