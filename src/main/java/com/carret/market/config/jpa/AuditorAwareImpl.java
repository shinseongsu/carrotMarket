package com.carret.market.config.jpa;

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
        String modifiedBy =
            isConvretAuthentication(authentication) ? httpServletRequest.getRequestURI() :
                authentication.getName();

        return Optional.of(modifiedBy);
    }

    private boolean isConvretAuthentication(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }

}
