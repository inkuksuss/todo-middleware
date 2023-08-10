package com.project.todo.service;

import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.dto.MemberSearchCond;
import com.project.todo.domain.dto.PageDto;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.exception.NoMatchPasswordException;
import com.project.todo.exception.NotFoundMemberException;
import com.project.todo.repository.member.MemberRepository;
import com.project.todo.utils.constant.PageConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Slf4j
@Service
@Validated
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberDto doJoin(MemberDto memberDto) {

        if (!StringUtils.hasText(memberDto.getEmail())) {
            throw new IllegalArgumentException("email can not be null");
        }

        if (!StringUtils.hasText(memberDto.getName())) {
            throw new IllegalArgumentException("name can not be null");
        }

        if (!StringUtils.hasText(memberDto.getPassword())) {
            throw new IllegalArgumentException("password can not be null");
        }

        Optional<Member> sameEmailMember = memberRepository.findByEmail(memberDto.getEmail());
        sameEmailMember.ifPresent(m -> {
            throw new DuplicateEmailException();
        });

        // need encrypt
        Member member = new Member(memberDto.getName(), memberDto.getEmail(), memberDto.getPassword(), MEMBER_TYPE.MEMBER);
        Member savedMember = memberRepository.save(member);

        return MemberDto.fromEntity(savedMember);
    }

    public MemberDto doLogin(String email, String password) {

        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("email can not be null");
        }

        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("password can not be null");
        }

        Optional<Member> findMember = memberRepository.findByEmail(email);
        Member member = findMember.orElseThrow(NotFoundMemberException::new);

        if (!member.getPassword().equals(password)) {
            throw new NoMatchPasswordException();
        }

        return MemberDto.fromEntity(member);
    }

    public PageDto<MemberDto> searchMemberList(MemberSearchCond cond) {
        PageRequest pageRequest = PageRequest.of(
                cond.getPage() == null ? PageConst.DEFAULT_PAGE : cond.getPage(),
                cond.getSize() == null ? PageConst.DEFAULT_PAGE_SIZE : cond.getSize()
        );

        Page<Member> memberList = memberRepository.findPagingMemberList(cond, pageRequest);

        return new PageDto<>(
                memberList.getTotalElements(),
                memberList.getTotalPages(),
                memberList.hasNext(),
                memberList.map(MemberDto::fromEntity).toList()
        );
    }
}
