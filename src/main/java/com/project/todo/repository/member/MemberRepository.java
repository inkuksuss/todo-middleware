package com.project.todo.repository.member;

import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    List<Member> findByIdInAndType(List<Long> ids, MEMBER_TYPE type);
}
