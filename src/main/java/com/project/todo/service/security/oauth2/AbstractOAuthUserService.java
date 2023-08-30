package com.project.todo.service.security.oauth2;

import com.project.todo.domain.entity.Member;
import com.project.todo.domain.model.GoogleUser;
import com.project.todo.domain.model.NaverUser;
import com.project.todo.domain.model.ProviderUser;
import com.project.todo.domain.types.MEMBER_TYPE;
import com.project.todo.factory.oauth.OauthUserFactory;
import com.project.todo.repository.member.MemberRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public abstract class AbstractOAuthUserService {

    private final MemberRepository memberRepository;

    public AbstractOAuthUserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    protected ProviderUser getProviderUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        OauthUserFactory oauthUserFactory = new OauthUserFactory(clientRegistration);
        return oauthUserFactory.createUser(oAuth2User);
    }

    protected void processSave(ProviderUser providerUser) {
        Optional<Member> member = this.memberRepository.findByEmail(providerUser.getEmail());
        if (member.isPresent()) {
            this.updateIfNoExisted(providerUser, member.get());
        }
        else {
            memberRepository.save(
                    Member.createOAuthMember(
                            providerUser.getUsername(),
                            providerUser.getPassword(),
                            providerUser.getEmail(),
                            providerUser.getProvider())
            );
        }
    }

    private void updateIfNoExisted(ProviderUser providerUser, Member savedMember) {
        if (providerUser.getProvider() == savedMember.getProvider()) {
            savedMember.setEmail(providerUser.getEmail());
            savedMember.setName(providerUser.getUsername());
            savedMember.setPassword(providerUser.getPassword());
            savedMember.setProvider(providerUser.getProvider());
            savedMember.setType(MEMBER_TYPE.MEMBER);
            memberRepository.save(savedMember);
        }
        else {
            throw new IllegalStateException("이미 가입된 다른 서비스가 있습니다.");
        }
    }
}
