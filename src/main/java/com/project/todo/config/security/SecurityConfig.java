package com.project.todo.config.security;

import com.project.todo.config.security.filter.JwtAuthenticationFilter;
import com.project.todo.config.security.provider.JwtTokenProvider;
import com.project.todo.service.security.CustomUserService;
import com.project.todo.service.security.oauth2.CustomOAuthUserService;
import com.project.todo.service.security.oauth2.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    CustomOAuthUserService customOAuth2UserService;

    @Autowired
    CustomOidcUserService customOidcUserService;

    @Value("${secret-key}")
    private String secretKey;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(customUserService, passwordEncoder(), secretKey);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/member/**").permitAll()
                            .requestMatchers("/todo/**").permitAll()
                            .requestMatchers("/**").permitAll();
                });
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        http
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2Client(oauth2 -> {
            oauth2.authorizationCodeGrant(
                    authorizationCodeGrantConfigurer -> authorizationCodeGrantConfigurer.authorizationRequestResolver(authorizationRequestResolver()));
        });
        http.oauth2Login(config -> {
            config.userInfoEndpoint(
                    userConfig -> {
                        userConfig
                                .oidcUserService(customOidcUserService)
                                .userService(customOAuth2UserService);
                    }
            );
        });
        http.addFilterBefore(
                new JwtAuthenticationFilter(jwtTokenProvider()),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public OAuth2AuthorizationRequestResolver authorizationRequestResolver() {
        DefaultOAuth2AuthorizationRequestResolver defaultOAuth2AuthorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization");
        defaultOAuth2AuthorizationRequestResolver.setAuthorizationRequestCustomizer(request -> request.state("aaaaaaaaaaaaaaa"));
        return defaultOAuth2AuthorizationRequestResolver;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/error");
    }
}
