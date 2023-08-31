package com.project.todo.service.security;

import com.project.todo.common.factory.authentication.MemberAuthenticationFactory;
import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.domain.dto.MemberContext;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.model.member.MemberAuthentication;
import com.project.todo.domain.model.member.MemberPrincipal;
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

            MemberAuthenticationFactory factory = new MemberAuthenticationFactory();
            MemberPrincipal memberPrincipal = factory.createMemberPrincipal(MemberAuthenticationFactoryForm.createTodoMemberForm(member));

            return new MemberAuthentication(memberPrincipal);
        } catch (NumberFormatException e) {
            throw new InsufficientAuthenticationException("invalid token");
        }
    }
}
