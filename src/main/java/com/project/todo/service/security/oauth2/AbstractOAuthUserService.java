package com.project.todo.service.security.oauth2;

import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.domain.dto.MemberDto;
import com.project.todo.domain.entity.Member;
import com.project.todo.domain.model.member.MemberHolder;
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

    protected MemberHolder getMemberPrincipal(MemberAuthenticationFactoryForm form) {
        MemberAuthenticationFactory oauthUserFactory = new MemberAuthenticationFactory();
        return oauthUserFactory.createMemberPrincipal(form);
    }

    protected void processSave(MemberHolder memberHolder) {
        Assert.notNull(memberHolder, "memberPrincipal cannot be null");

        Optional<Member> member = this.memberRepository.findByEmail(memberHolder.getEmail());
        Member savedMember;
        savedMember = member
                .map(value -> this.updateIfNoExisted(memberHolder, value))
                .orElseGet(() -> saveMember(memberHolder));

        memberHolder.updateMember(MemberDto.fromEntity(savedMember));
    }

    private Member updateIfNoExisted(MemberHolder memberHolder, Member savedMember) {
        if (memberHolder.getProvider() == savedMember.getProvider()) {
            savedMember.setEmail(memberHolder.getMember().getEmail());
            savedMember.setName(memberHolder.getMember().getName());
            savedMember.setPassword(memberHolder.getMember().getPassword());
            savedMember.setProvider(memberHolder.getMember().getProvider());
            savedMember.setType(memberHolder.getMember().getType());
            return memberRepository.save(savedMember);
        }
        else {
            throw new IllegalStateException("이미 가입된 다른 서비스가 있습니다.");
        }
    }

    private Member saveMember(MemberHolder memberHolder) {
        MemberDto memberDto = memberHolder.getMember();
        return memberRepository.save(new Member(
                memberDto.getName(),
                memberDto.getEmail(),
                memberDto.getPassword(),
                memberDto.getType(),
                memberDto.getProvider()
        ));
    }
}
