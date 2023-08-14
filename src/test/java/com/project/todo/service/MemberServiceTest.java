package com.project.todo.service;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.dto.MemberSearchCond;
import com.project.todo.domain.dto.PageDto;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.repository.member.MemberJdbcRepository;
import com.project.todo.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired MemberJdbcRepository memberJdbcRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void doJoin() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setEmail("test@naver.com");
        memberDto.setPassword("111naver.com");

        MemberDto saveMember = memberService.doJoin(memberDto);

        assertThat(memberDto.getName()).isEqualTo(saveMember.getName());
        assertThat(memberDto.getEmail()).isEqualTo(saveMember.getEmail());
        assertThat(saveMember.getPassword()).isNull();
    }

    @Test
    void doJoinInvalidEmailFormat() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setEmail("tesnaver.com");
        memberDto.setPassword("111");


        assertThatThrownBy(() -> memberService.doJoin(memberDto)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void jdbcTest() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setPassword("111");
        memberDto.setEmail("test@naver.com");

        MemberDto saveMember = memberService.doJoin(memberDto);
        entityManager.flush();
        entityManager.clear();

        Optional<MemberDto> jdbcMember = memberJdbcRepository.findJdbcById(saveMember.getId());
        log.info("jdbc = {}", jdbcMember.get());
    }

    @Test
    void duplicateEmail() {
        MemberDto memberDto1 = new MemberDto();
        memberDto1.setName("test");
        memberDto1.setPassword("111");
        memberDto1.setEmail("test@naver.com");

        MemberDto saveMember = memberService.doJoin(memberDto1);

        MemberDto memberDto2 = new MemberDto();
        memberDto2.setName("asdasd");
        memberDto2.setPassword("111");
        memberDto2.setEmail("test@naver.com");

        assertThatThrownBy(() -> memberService.doJoin(memberDto2)).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void doLogin() {
        //given
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setPassword("111");
        memberDto.setEmail("test@naver.com");
        MemberDto saveMember = memberService.doJoin(memberDto);

        //when
        saveMember.setPassword(memberDto.getPassword());
        MemberDto loginMember = memberService.doLogin(saveMember.getEmail(), saveMember.getPassword());

        //then
        assertThat(loginMember.getId()).isEqualTo(saveMember.getId());
        assertThat(loginMember.getType()).isEqualTo(MEMBER_TYPE.MEMBER);
    }

    @Test
    void noEmailLogin() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setPassword("111");
        memberDto.setEmail("test@naver.com");

        MemberDto saveMember = memberService.doJoin(memberDto);

        saveMember.setEmail("wrong");
        saveMember.setPassword(memberDto.getPassword());
//        assertThatThrownBy(() -> memberService.doLogin(saveMember.getEmail(), saveMember.getPassword())).isInstanceOf(NotFoundMemberException.class);
    }

    @Test
    void notMatchPasswordLogin() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("test");
        memberDto.setPassword("111");
        memberDto.setEmail("test@naver.com");

        MemberDto saveMember = memberService.doJoin(memberDto);

        saveMember.setPassword("wrong");
//        assertThatThrownBy(() -> memberService.doLogin(saveMember.getEmail(), saveMember.getPassword())).isInstanceOf(NoMatchPasswordException.class);
    }

    @Test
    void pagingTest() {
        for (int i = 0; i < 100; i++) {
            MemberDto memberDto = new MemberDto();
            memberDto.setName("test" + i);
            memberDto.setPassword("111");
            memberDto.setEmail("test@naver.com" + i);
            memberService.doJoin(memberDto);
        }

        MemberSearchCond cond = new MemberSearchCond();
        cond.setPage(2);
        cond.setSize(30);
        PageDto<MemberDto> pageDto = memberService.searchMemberList(cond);

        Iterable<Member> all = memberRepository.findAll();
        Iterator<Member> iterator = all.iterator();


        int totalCount = 0;
        while (iterator.hasNext()) {
            totalCount++;
            iterator.next();
        }

        log.info("iterator = {}", totalCount);

        assertThat(pageDto.getDataList().size()).isEqualTo(30);
        assertThat(pageDto.getTotalCount()).isEqualTo(totalCount);
        assertThat(pageDto.getTotalPage()).isEqualTo(4);

    }

    @Test
    void pagingNameTest() {
        for (int i = 0; i < 100; i++) {
            MemberDto memberDto = new MemberDto();
            memberDto.setName("test" + i);
            memberDto.setPassword("111");
            memberDto.setEmail("test@naver.com" + i);
            memberService.doJoin(memberDto);
        }

        MemberSearchCond cond = new MemberSearchCond();
        cond.setPage(0);
        cond.setSize(30);
        cond.setEmail("test@naver.com61");
        PageDto<MemberDto> pageDto = memberService.searchMemberList(cond);

        log.info("pageDto = {}", pageDto.getDataList());

        assertThat(pageDto.getDataList().size()).isEqualTo(1);
        assertThat(pageDto.getTotalCount()).isEqualTo(1);
        assertThat(pageDto.getTotalPage()).isEqualTo(1);
    }
}