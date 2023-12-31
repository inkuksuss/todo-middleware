package com.project.todo.repository.todo;

import com.project.todo.domain.entity.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long>, TodoRepositoryCustom {

    @Query("SELECT t FROM Todo t where t.member.id = :id")
    List<Todo> findByMemberId(Long id);

}
