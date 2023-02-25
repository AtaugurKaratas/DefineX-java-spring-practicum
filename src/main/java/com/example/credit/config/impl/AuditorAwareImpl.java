package com.example.credit.config.impl;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String identityNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(identityNumber);
    }
}
