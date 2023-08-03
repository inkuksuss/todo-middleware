package com.project.todo.repository;

import com.project.todo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);
}
