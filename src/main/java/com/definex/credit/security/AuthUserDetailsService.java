package com.definex.credit.security;

import com.definex.credit.model.Auth;
import com.definex.credit.repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    public AuthUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identityNumber) throws UsernameNotFoundException {
        Optional<Auth> userInfo = authRepository.findByIdentityNumber(identityNumber);
        return userInfo.map(AuthUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + identityNumber));
    }
}
