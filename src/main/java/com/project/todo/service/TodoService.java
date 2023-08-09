package com.project.todo.service;

import com.project.todo.domain.dto.TodoDto;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.entity.Todo;
import com.project.todo.repository.member.MemberRepository;
import com.project.todo.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoDto saveTodo(TodoDto todoDto) {

        Optional<Member> findMember = memberRepository.findById(todoDto.getMemberId());

        // NotFoundMember
        Member member = findMember.orElseThrow(IllegalStateException::new);

        Todo savedTodo = null;

        if (todoDto.getTodoId() != null) {
            Optional<Todo> findTodo = todoRepository.findById(todoDto.getTodoId());

            // NotfoundTodo
            Todo todo = findTodo.orElseThrow(IllegalStateException::new);
            todo.setType(todoDto.getType());
            todo.setTitle(todoDto.getTitle());
            todo.setContent(todoDto.getContent());
            todo.setMember(member);

            savedTodo = todoRepository.save(todo);
        } else {
            Todo newTodo = new Todo(TODO_TYPE.COMMON, todoDto.getTitle(), todoDto.getContent());

            newTodo.setMember(member);

            savedTodo = todoRepository.save(newTodo);
        }

        return TodoDto.fromEntity(savedTodo);
    }

    public TodoDto findTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        return TodoDto.fromEntity(todo);
    }
}
