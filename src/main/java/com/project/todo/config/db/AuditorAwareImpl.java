package com.project.todo.config.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth = {}", authentication);

        if (authentication == null ||
                authentication.getPrincipal() == null ||
                !(authentication.getPrincipal() instanceof  MemberContext)) {
            return Optional.empty();
        }

        MemberContext memberContext = (MemberContext) authentication.getPrincipal();
        return Optional.of(memberContext.getMember().getId());
    }
}
