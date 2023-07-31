package com.project.todo.service;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.entity.Member;
import com.project.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto findDefaultMember() {
        Optional<Member> defaultMember = memberRepository.findByName("테스트 유저");

        Member member = defaultMember.orElseGet(this::createDefaultMember);

        return MemberDto.fromEntity(member);
    }

    @Transactional
    public MemberDto saveMember(MemberDto memberDto) {
        Member member = new Member(memberDto.getName(), memberDto.getEmail(), memberDto.getPassword());

        Member savedMember = memberRepository.save(member);

        return MemberDto.fromEntity(savedMember);
    }

    private Member createDefaultMember() {
        Member defaultMember = new Member("테스트 유저", "default@naver.com", "1111");

        return memberRepository.save(defaultMember);
    };

}
