package com.project.todo.repository.member;

import com.project.todo.domain.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);
}
