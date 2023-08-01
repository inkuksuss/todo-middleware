package com.project.todo.service;

import com.project.todo.domain.factory.dtofactory.dto.MemberAndTodoDto;
import com.project.todo.domain.factory.dtofactory.dto.TodoDto;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.entity.Member;
import com.project.todo.entity.Todo;
import com.project.todo.repository.MemberRepository;
import com.project.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoDto saveTodo(MemberAndTodoDto dto) {

        Optional<Member> findMember = memberRepository.findById(dto.getMemberId());

        // NotFoundMember
        Member member = findMember.orElseThrow(IllegalStateException::new);

        Todo savedTodo = null;

        if (dto.getTodoId() != null) {
            Optional<Todo> findTodo = todoRepository.findById(dto.getTodoId());

            // NotfoundTodo
            Todo todo = findTodo.orElseThrow(IllegalStateException::new);
            todo.setType(dto.getTodoType());
            todo.setTitle(dto.getTodoTitle());
            todo.setContent(dto.getTodoContent());
            todo.setMember(member);

            savedTodo = todoRepository.save(todo);
        } else {
            Todo newTodo = new Todo(TODO_TYPE.COMMON, dto.getTodoTitle(), dto.getTodoContent());
            newTodo.setMember(member);

            savedTodo = todoRepository.save(newTodo);
        }

        return TodoDto.fromEntity(savedTodo);
    }

    @Transactional
    public TodoDto findTodo(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        return TodoDto.fromEntity(todo.get());
    }
}
