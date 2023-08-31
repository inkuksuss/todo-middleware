package com.project.todo.service.security.oauth2;

import com.project.todo.common.converter.DelegatingMemberPrincipalConverter;
import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.domain.model.member.MemberAuthentication;
import com.project.todo.domain.model.member.MemberPrincipal;
import com.project.todo.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomOAuthUserService extends AbstractOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    public CustomOAuthUserService(MemberRepository memberRepository) {
        super(memberRepository);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuthUserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuthUserService.loadUser(userRequest);

        MemberAuthenticationFactoryForm form = MemberAuthenticationFactoryForm.createOAuthMemberForm(oAuth2User, clientRegistration);
        MemberPrincipal memberPrincipal = super.getMemberPrincipal(form);

        super.processSave(memberPrincipal);

        return new MemberAuthentication(memberPrincipal);
    }
}
