package com.example.credit.controller;

import com.example.credit.dto.request.*;
import com.example.credit.dto.response.AuthTokenResponse;
import com.example.credit.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/customer/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addNewCustomer(@Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
        authService.addUserCustomer(authRegisterRequest);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthTokenResponse authenticateAndGetToken(@Valid @RequestBody AuthLoginRequest authRequest) {
        return authService.authenticateAndGetToken(authRequest);
    }

    @PutMapping("/passwordChange")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER') or hasAuthority('ROLE_EMPLOYEE') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePassword){
        if(authService.changePassword(changePassword))
            return new ResponseEntity<>("The Password Has Been Changed", HttpStatus.OK);
        else
            return new ResponseEntity<>("The Login Process Failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{authId}/{verifyCode}")
    public void accountActivation(@PathVariable String authId, @PathVariable String verifyCode){
        authService.accountActivation(authId, verifyCode);
    }

    @PostMapping("/forgottenPassword")
    public void forgottenPassword(@Valid @RequestBody ForgottenPasswordRequest forgottenPasswordRequest){
        authService.forgottenPassword(forgottenPasswordRequest);
    }

    @GetMapping("/forgottenPassword/{authId}/{verifyCode}")
    public void changeForgottenPassword(@PathVariable String authId, @PathVariable String verifyCode,
                                        @Valid @RequestBody ChangeForgottenPasswordRequest changeForgottenPasswordRequest){
        authService.changeForgottenPassword(authId, verifyCode, changeForgottenPasswordRequest);
    }

    @DeleteMapping("/delete/{authId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public void deleteUser(@PathVariable String authId){
        authService.deleteUser(authId);
    }
}