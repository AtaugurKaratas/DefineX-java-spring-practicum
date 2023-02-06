package com.example.credit.controller;

import com.example.credit.dto.request.AuthLoginRequest;
import com.example.credit.dto.request.AuthRegisterRequest;
import com.example.credit.dto.response.AuthTokenResponse;
import com.example.credit.security.JwtService;
import com.example.credit.service.impl.AuthServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthServiceImpl userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/customer/register")
    public AuthTokenResponse addNewCustomer(@RequestBody AuthRegisterRequest authRegisterRequest) {
        String id = userService.addUserCustomer(authRegisterRequest);
        AuthTokenResponse authResponse = new AuthTokenResponse(id, jwtService.generateToken(authRegisterRequest.getIdentityNumber()));
        return authResponse;
    }

    @PostMapping("/customer/login")
    public AuthTokenResponse authenticateAndGetToken(@RequestBody AuthLoginRequest authRequest) {
        AuthTokenResponse authResponse;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (authRequest.getIdentityNumber(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String id = userService.getUserId(authRequest.getIdentityNumber());
            authResponse = new AuthTokenResponse(id, jwtService.generateToken(authRequest.getIdentityNumber()));
            return authResponse;
        } else
            throw new UsernameNotFoundException("Invalid user request");
    }

}
