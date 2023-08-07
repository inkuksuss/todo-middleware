package com.project.todo.factory.dtofactory;

public interface DtoFactory<T, S> {

    T createDto(S s);
}
