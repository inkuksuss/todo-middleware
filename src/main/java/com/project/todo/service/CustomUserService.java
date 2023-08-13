package com.project.todo.service;

import com.project.todo.domain.dto.MemberContext;
import com.project.todo.domain.entity.Member;
import com.project.todo.repository.member.MemberRepository;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        if (!StringUtils.hasText(memberId)) {
            throw new IllegalArgumentException("token can not be null");
        }

        try {
            Optional<Member> findMember = memberRepository.findById(Long.valueOf(memberId));
            Member member = findMember
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));


            return new MemberContext(member, Set.of(new SimpleGrantedAuthority(member.getType().getRole())));
        } catch (NumberFormatException e) {
            throw new InsufficientAuthenticationException("invalid token");
        }
    }
}
