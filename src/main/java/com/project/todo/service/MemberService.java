package com.project.todo.service;

import com.project.todo.domain.factory.dtofactory.dto.MemberDto;
import com.project.todo.entity.Member;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.exception.NoMatchPasswordException;
import com.project.todo.exception.NotFoundMemberException;
import com.project.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto doJoin(MemberDto memberDto) {

        Optional<Member> sameNameMember = memberRepository.findByName(memberDto.getName());
        sameNameMember.orElseThrow(() -> new DuplicateEmailException("duplicate name"));

        // need encrypt
        Member member = new Member(memberDto.getName(), memberDto.getEmail(), memberDto.getPassword());
        Member savedMember = memberRepository.save(member);

        return MemberDto.fromEntity(savedMember);
    }

    public MemberDto doLogin(MemberDto memberDto) {

        Optional<Member> findMember = memberRepository.findByEmail(memberDto.getName());
        Member member = findMember.orElseThrow(NotFoundMemberException::new);

        if (!member.getPassword().equals(memberDto.getPassword())) {
            throw new NoMatchPasswordException();
        }

        return MemberDto.fromEntity(member);
    }
}
