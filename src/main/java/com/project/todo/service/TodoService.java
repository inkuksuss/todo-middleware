package com.project.todo.service;

import com.project.todo.domain.dto.TodoDto;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.entity.Todo;
import com.project.todo.repository.member.MemberRepository;
import com.project.todo.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

        if (!StringUtils.hasText(todoDto.getTitle()) || !StringUtils.hasText(todoDto.getContent())) {
            throw new IllegalArgumentException("title or content cannot be null");
        }

        Member member = validMember(todoDto);

        Todo todo = new Todo(todoDto.getType() != null ? todoDto.getType() : TODO_TYPE.COMMON, todoDto.getTitle(), todoDto.getContent());
        todo.changeMember(member);

        Todo savedTodo = todoRepository.save(todo);

        return TodoDto.fromEntity(savedTodo);
    }

    @Transactional
    public TodoDto updateTodo(TodoDto todoDto) {
        Member member = validMember(todoDto);

        Optional<Todo> findTodo = todoRepository.findById(todoDto.getTodoId());
        Todo todo = findTodo.orElseThrow(NoSuchElementException::new);

        todo.changeMember(member);

        if (StringUtils.hasText(todoDto.getTitle())) {
            todo.setTitle(todoDto.getTitle());
        }

        if (StringUtils.hasText(todoDto.getContent())) {
            todo.setContent(todoDto.getContent());
        }

        Todo updatedMember = todoRepository.save(todo);

        return TodoDto.fromEntity(updatedMember);
    }

    @Transactional
    public void removeOneTodo(Long memberId, Long todoId) {

        if (todoId == null) {
            throw new IllegalArgumentException("todo id can not be null");
        }

        if (memberId == null) {
            throw new IllegalArgumentException("member id can not be null");
        }

        Long deleteCount = todoRepository.deleteDynamicTodo(todoId, memberId);

        if (deleteCount < 1) {
            throw new NoSuchElementException("delete target is empty");
        }
    }

    public TodoDto findTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        return TodoDto.fromEntity(todo);
    }

    private Member validMember(TodoDto todoDto) {

        if (todoDto.getMemberId() == null) {
            throw new IllegalArgumentException("member id cannot be null");
        }

        Optional<Member> findMember = memberRepository.findById(todoDto.getMemberId());

        return findMember.orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
