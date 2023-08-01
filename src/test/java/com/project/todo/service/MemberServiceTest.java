package com.project.todo.service;

import com.project.todo.entity.Member;
import com.project.todo.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void hasTx() {
        Member member = new Member("test1", "test@naver.com", "1111");
        memberRepository.save(member);
    }

    @Test
    void crudHasFindByName() {
        Member member = new Member("test1", "test@naver.com", "1111");
        System.out.println("1");
        memberRepository.save(member);
        System.out.println("2");

        Optional<Member> testMember = memberRepository.findByName("test1");
        System.out.println("3");
        Assertions.assertThat(testMember.get().getName()).isEqualTo("test1");
    }

    @Test
    void testTx() {
        Member newMember = new Member("test1", "test1@naver.com", "1111");
        memberService.testMember(newMember);
    }

}