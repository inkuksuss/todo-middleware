package com.project.todo.domain.factory.dtofactory;

import com.project.todo.controller.request.RequestObejct;
import com.project.todo.domain.factory.dtofactory.dto.CustomDto;

public interface DtoFactory<T extends RequestObejct> {

    CustomDto createDto(T t);
}
