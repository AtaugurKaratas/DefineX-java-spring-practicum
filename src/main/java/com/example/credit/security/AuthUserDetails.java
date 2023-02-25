package com.example.credit.security;

import com.example.credit.dto.AuthDto;
import com.example.credit.model.Auth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class AuthUserDetails implements UserDetails {

    AuthDto authDto;

    public AuthUserDetails(Auth auth) {
        authDto = new AuthDto(
                auth.getIdentityNumber(),
                auth.getPassword(),
                Arrays.stream(auth.getRoles().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()),
                auth.isAccountNonLocked(),
                auth.isEnabled()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authDto.authorities();
    }

    @Override
    public String getPassword() {
        return authDto.password();
    }

    @Override
    public String getUsername() {
        return authDto.identityNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return authDto.accountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return authDto.enabled();
    }

}