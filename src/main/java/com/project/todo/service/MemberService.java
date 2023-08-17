package com.project.todo.service;

import com.project.todo.config.security.provider.JwtTokenProvider;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.dto.MemberSearchCond;
import com.project.todo.domain.dto.PageDto;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.exception.DuplicateEmailException;
import com.project.todo.repository.member.MemberRepository;
import com.project.todo.utils.constants.PageConst;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Validated
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public MemberDto doJoin(@Valid MemberDto memberDto) {

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

        Member savedMember = memberRepository.save(new Member(
                memberDto.getName(),
                memberDto.getEmail(),
                jwtTokenProvider.encryptPassword(memberDto.getPassword()),
                MEMBER_TYPE.MEMBER
        ));

        log.info("saved = {}", savedMember.toString());

        return MemberDto.fromEntity(savedMember);
    }

    @Transactional
    public MemberDto doLogin(String email, String password) {

        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("email can not be null");
        }

        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("password can not be null");
        }

        Optional<Member> findMember = memberRepository.findByEmail(email);
        Member member = findMember
                .orElseThrow(() -> new UsernameNotFoundException("user is not found"));

        if (!jwtTokenProvider.checkPasswordMatch(password, member.getPassword())) {
            throw new BadCredentialsException("password is not match");
        }

        MemberDto memberDto = MemberDto.fromEntity(member);

        String token = jwtTokenProvider.createToken(
                member.getId(),
                email,
                Set.of(new SimpleGrantedAuthority(member.getType().getRole()))
        );
        memberDto.addToken(token);

        return memberDto;
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
