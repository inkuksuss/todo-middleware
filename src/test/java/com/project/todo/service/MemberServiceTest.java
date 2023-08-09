package com.project.todo.service;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.dto.MemberSearchCond;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.exception.NoMatchPasswordException;
import com.project.todo.exception.NotFoundMemberException;
import com.project.todo.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

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
        assertThat(loginMember.getType()).isEqualTo(MEMBER_TYPE.MEMBER);
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

    @Test
    void pagingTest() {
        for (int i = 0; i < 100; i++) {
            MemberDto memberDto = new MemberDto();
            memberDto.setName("test" + i);
            memberDto.setPassword("111");
            memberDto.setEmail("test" + i);
            memberService.doJoin(memberDto);
        }

        MemberSearchCond cond = new MemberSearchCond();
        cond.setPage(2);
        cond.setSize(30);
        Page<Member> members = memberService.searchMemberList(cond);

        for (Member member : members) {
            log.info("member = {}", member);
        }

        assertThat(members.getContent().size()).isEqualTo(30);
        assertThat(members.getTotalElements()).isEqualTo(100);
        assertThat(members.getTotalPages()).isEqualTo(4);

    }

    @Test
    void pagingNameTest() {
        for (int i = 0; i < 100; i++) {
            MemberDto memberDto = new MemberDto();
            memberDto.setName("test" + i);
            memberDto.setPassword("111");
            memberDto.setEmail("test" + i);
            memberService.doJoin(memberDto);
        }

        MemberSearchCond cond = new MemberSearchCond();
        cond.setPage(0);
        cond.setSize(30);
        cond.setEmail("test61");
        Page<Member> members = memberService.searchMemberList(cond);

        for (Member member : members) {
            log.info("member = {}", member);
        }

        assertThat(members.getContent().size()).isEqualTo(1);
        assertThat(members.getTotalElements()).isEqualTo(1);
        assertThat(members.getTotalPages()).isEqualTo(1);

    }
}