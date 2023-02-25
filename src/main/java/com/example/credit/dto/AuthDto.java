package com.example.credit.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record AuthDto(
        String identityNumber,
        String password,
        List<GrantedAuthority> authorities,
        boolean accountNonLocked,
        boolean enabled) {
}