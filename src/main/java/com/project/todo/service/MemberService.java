package com.project.todo.service;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.entity.Member;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.exception.NoMatchPasswordException;
import com.project.todo.exception.NotFoundMemberException;
import com.project.todo.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto doJoin(MemberDto memberDto) {

        Optional<Member> sameEmailMember = memberRepository.findByEmail(memberDto.getEmail());
        sameEmailMember.ifPresent(m -> {
            throw new DuplicateEmailException();
        });

        // need encrypt
        Member member = new Member(memberDto.getName(), memberDto.getEmail(), memberDto.getPassword());
        Member savedMember = memberRepository.save(member);

        return MemberDto.fromEntity(savedMember);
    }

    public MemberDto doLogin(@Valid MemberDto memberDto) {

        Optional<Member> findMember = memberRepository.findByEmail( memberDto.getEmail());
        Member member = findMember.orElseThrow(NotFoundMemberException::new);

        if (!member.getPassword().equals(memberDto.getPassword())) {
            throw new NoMatchPasswordException();
        }

        return MemberDto.fromEntity(member);
    }
}
