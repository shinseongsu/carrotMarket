package com.carret.market.global.config;

import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String EMPTY = "";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String modifiedBy = EMPTY;
        try {
            modifiedBy = isConvretAuthentication(authentication) ?
                httpServletRequest.getRequestURI() :
                authentication.getName();
        } catch (Exception e) {
            modifiedBy = EMPTY;
        }

        return Optional.of(modifiedBy);
    }

    private boolean isConvretAuthentication(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken || Objects.isNull(authentication);
    }

}
