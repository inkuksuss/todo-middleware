package com.project.todo.domain.factory.dtofactory;

import com.project.todo.domain.factory.dtofactory.dto.CustomDto;
import com.project.todo.domain.request.RequestObject;

public interface DtoFactory<T extends RequestObject> {

    CustomDto createDto(T t);
}
