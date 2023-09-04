package com.project.todo.service;

import com.project.todo.repository.condition.TodoSearchCond;
import com.project.todo.service.dto.todo.TodoSearchDto;
import com.project.todo.service.dto.PageDto;
import com.project.todo.service.dto.todo.TodoDto;
import com.project.todo.domain.types.RELATIONSHIP_TYPE;
import com.project.todo.domain.types.TODO_TYPE;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.entity.Todo;
import com.project.todo.repository.friend.FriendRepository;
import com.project.todo.repository.member.MemberRepository;
import com.project.todo.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    private final FriendRepository friendRepository;

    @Transactional
    public TodoDto saveTodo(TodoDto todoDto) {

        if (!StringUtils.hasText(todoDto.getTitle()) || !StringUtils.hasText(todoDto.getContent())) {
            throw new IllegalArgumentException("title or content cannot be null");
        }

        Member member = validMember(todoDto);

        Todo todo = new Todo(todoDto.getType() != null ? todoDto.getType() : TODO_TYPE.PRIVATE, todoDto.getTitle(), todoDto.getContent());
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

        if (todoDto.getType() != null) {
            todo.setType(todoDto.getType());
        }

        Todo updatedMember = todoRepository.save(todo);

        return TodoDto.fromEntity(updatedMember);
    }

    @Transactional
    public void removeTodoOne(Long memberId, Long todoId) {

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

    public PageDto<TodoDto> getTodoListOfMember(TodoSearchDto dto, Pageable pageable) {
        RELATIONSHIP_TYPE type = determineRelationship(dto.getMemberId(), dto.getRequestMemberId());

        TodoSearchCond cond = new TodoSearchCond();
        cond.setTodoTitle(dto.getTodoTitle());
        cond.setIsComplete(dto.getIsComplete());
        cond.setType(type);


        todoRepository.findTodoListOfMember(cond, pageable);
    }

    public PageDto<TodoDto> getSearchTodoList(TodoSearchDto cond) {

        // memberid


    }

    public TodoDto findTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        return TodoDto.fromEntity(todo);
    }

    private RELATIONSHIP_TYPE determineRelationship(Long targetId, Long requestId) {
        if (requestId != null && requestId.equals(targetId)) {
            return RELATIONSHIP_TYPE.ME;
        }

        if (requestId != null && targetId != null) {
            return friendRepository.checkIsFriend(requestId, targetId)
                    .map(v -> RELATIONSHIP_TYPE.FRIEND)
                    .orElse(RELATIONSHIP_TYPE.NONE);
        }

        return RELATIONSHIP_TYPE.NONE;
    }

    private Member validMember(TodoDto todoDto) {

        if (todoDto.getMemberId() == null) {
            throw new IllegalArgumentException("member id cannot be null");
        }

        Optional<Member> findMember = memberRepository.findById(todoDto.getMemberId());

        return findMember.orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
