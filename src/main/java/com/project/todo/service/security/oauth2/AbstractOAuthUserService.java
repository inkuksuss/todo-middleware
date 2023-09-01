package com.project.todo.service.security.oauth2;

import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.model.member.MemberPrincipal;
import com.project.todo.common.factory.authentication.MemberAuthenticationFactory;
import com.project.todo.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;


@Service
public abstract class AbstractOAuthUserService {

    private final MemberRepository memberRepository;

    public AbstractOAuthUserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    protected MemberPrincipal getMemberPrincipal(MemberAuthenticationFactoryForm form) {
        MemberAuthenticationFactory oauthUserFactory = new MemberAuthenticationFactory();
        return oauthUserFactory.createMemberPrincipal(form);
    }

    protected void processSave(MemberPrincipal memberPrincipal) {
        Assert.notNull(memberPrincipal, "memberPrincipal cannot be null");

        Optional<Member> member = this.memberRepository.findByEmail(memberPrincipal.getEmail());
        Member savedMember;
        savedMember = member
                .map(value -> this.updateIfNoExisted(memberPrincipal, value))
                .orElseGet(() -> saveMember(memberPrincipal));

        if (memberPrincipal.getMember().getId() == null) {
            memberPrincipal.getMember().setId(savedMember.getId());
        }
    }

    private Member updateIfNoExisted(MemberPrincipal memberPrincipal, Member savedMember) {
        if (memberPrincipal.getProvider() == savedMember.getProvider()) {
            savedMember.setEmail(memberPrincipal.getMember().getEmail());
            savedMember.setName(memberPrincipal.getMember().getName());
            savedMember.setPassword(memberPrincipal.getMember().getPassword());
            savedMember.setProvider(memberPrincipal.getMember().getProvider());
            savedMember.setType(memberPrincipal.getMember().getType());
            return memberRepository.save(savedMember);
        }
        else {
            throw new IllegalStateException("이미 가입된 다른 서비스가 있습니다.");
        }
    }

    private Member saveMember(MemberPrincipal memberPrincipal) {
        MemberDto memberDto = memberPrincipal.getMember();
        return memberRepository.save(new Member(
                memberDto.getName(),
                memberDto.getEmail(),
                memberDto.getPassword(),
                memberDto.getType(),
                memberDto.getProvider()
        ));
    }
}
