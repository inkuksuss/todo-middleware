package com.project.todo.service.security.oauth2;

import com.project.todo.common.factory.authentication.MemberAuthenticationFactoryForm;
import com.project.todo.domain.model.member.MemberAuthentication;
import com.project.todo.domain.model.member.MemberPrincipal;
import com.project.todo.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomOidcUserService extends AbstractOAuthUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    public CustomOidcUserService(MemberRepository memberRepository) {
        super(memberRepository);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);

        MemberAuthenticationFactoryForm form = MemberAuthenticationFactoryForm.createOAuthMemberForm(oidcUser, clientRegistration);
        MemberPrincipal memberPrincipal = super.getMemberPrincipal(form);

        super.processSave(memberPrincipal);

        return new MemberAuthentication(memberPrincipal);

    }
}
