package com.project.todo.repository.member;

import com.project.todo.domain.dto.MemberSearchCond;
import com.project.todo.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

    Page<Member> findPagingMemberList(MemberSearchCond cond, Pageable pageable);
}
