package com.project.todo.service;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.exception.NoMatchPasswordException;
import com.project.todo.exception.NotFoundMemberException;
import com.project.todo.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void doJoin() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setPassword("111");
        memberDto.setEmail("test");

        MemberDto saveMember = memberService.doJoin(memberDto);

        assertThat(memberDto.getName()).isEqualTo(saveMember.getName());
        assertThat(memberDto.getEmail()).isEqualTo(saveMember.getEmail());
        assertThat(saveMember.getPassword()).isNull();
    }

    @Test
    void duplicateEmail() {
        MemberDto memberDto1 = new MemberDto();
        memberDto1.setName("test");
        memberDto1.setPassword("111");
        memberDto1.setEmail("test");

        MemberDto saveMember = memberService.doJoin(memberDto1);

        MemberDto memberDto2 = new MemberDto();
        memberDto2.setName("asdasd");
        memberDto2.setPassword("111");
        memberDto2.setEmail("test");

        assertThatThrownBy(() -> memberService.doJoin(memberDto2)).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void doLogin() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setPassword("111");
        memberDto.setEmail("test");

        MemberDto saveMember = memberService.doJoin(memberDto);

        saveMember.setPassword(memberDto.getPassword());
        MemberDto loginMember = memberService.doLogin(saveMember.getEmail(), saveMember.getPassword());

        assertThat(loginMember.getId()).isEqualTo(saveMember.getId());
    }

    @Test
    void noEmailLogin() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setPassword("111");
        memberDto.setEmail("test");

        MemberDto saveMember = memberService.doJoin(memberDto);

        saveMember.setEmail("wrong");
        saveMember.setPassword(memberDto.getPassword());
        assertThatThrownBy(() -> memberService.doLogin(saveMember.getEmail(), saveMember.getPassword())).isInstanceOf(NotFoundMemberException.class);
    }

    @Test
    void notMatchPasswordLogin() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setPassword("111");
        memberDto.setEmail("test");

        MemberDto saveMember = memberService.doJoin(memberDto);

        saveMember.setPassword("wrong");
        assertThatThrownBy(() -> memberService.doLogin(saveMember.getEmail(), saveMember.getPassword())).isInstanceOf(NoMatchPasswordException.class);
    }
}