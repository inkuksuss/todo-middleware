package com.project.todo.common.factory.authentication;

import com.project.todo.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

@Getter
public class MemberAuthenticationFactoryForm {

    private OAuth2User oAuth2User;

    private ClientRegistration clientRegistration;

    private Member member;

    private MemberAuthenticationFactoryForm() {}

    public static MemberAuthenticationFactoryForm createTodoMemberForm(Member member) {
        Assert.notNull(member, "member cannot be null");

        MemberAuthenticationFactoryForm form = new MemberAuthenticationFactoryForm();
        form.member = member;

        return form;
    }

    public static MemberAuthenticationFactoryForm createOAuthMemberForm(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        Assert.notNull(oAuth2User, "oAuth2User cannot be null");
        Assert.notNull(clientRegistration, "clientRegistration cannot be null");

        MemberAuthenticationFactoryForm form = new MemberAuthenticationFactoryForm();
        form.oAuth2User = oAuth2User;
        form.clientRegistration = clientRegistration;

        return form;
    }
}
