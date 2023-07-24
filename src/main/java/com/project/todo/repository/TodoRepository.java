package com.project.todo.repository;

import com.project.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t where t.member.memberId = ?1")
    List<Todo> findAllByMemberId(Long memberSeq);

    Optional<Todo> findByTodoSeq(Long todoSeq);
}
