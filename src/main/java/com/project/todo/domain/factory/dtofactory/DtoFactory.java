package com.project.todo.domain.factory.dtofactory;

import com.project.todo.controller.request.RequestObject;
import com.project.todo.domain.factory.dtofactory.dto.CustomDto;

public interface DtoFactory<T extends RequestObject> {

    CustomDto createDto(T t);
}
