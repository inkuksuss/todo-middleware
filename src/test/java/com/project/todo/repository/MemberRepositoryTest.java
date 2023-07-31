package com.project.todo.repository;

import com.project.todo.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    public void save() {
//        Member member = new Member();
//        member.setMemberId("test1");
//        member.setMemberEmail("inkuksuss@naver.com");
//        member.setMemberPassword("1234");
//        member.setMemberName("inguk");
//
//        Member savedMember = memberRepository.save(member);
//
//        org.assertj.core.api.Assertions.assertThat(savedMember).isNotNull();
    }
}