package com.project.todo.service;

import com.project.todo.entity.Member;
import com.project.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member findDefaultMember() {
        Optional<Member> defaultMember = memberRepository.findByName("inguk");

        return defaultMember.orElseGet(this::createDefaultMember);
    }

    private Member createDefaultMember() {
        Member defaultMember = new Member("테스트 유저", "default@naver.com", "1111");

        return memberRepository.save(defaultMember);
    };

}
