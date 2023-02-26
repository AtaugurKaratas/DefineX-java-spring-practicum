package com.example.credit.service.impl;

import com.example.credit.dto.request.*;
import com.example.credit.dto.response.AuthTokenResponse;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Auth;
import com.example.credit.model.enumeration.RoleType;
import com.example.credit.repository.AuthRepository;
import com.example.credit.security.JwtService;
import com.example.credit.service.AuthService;
import com.example.credit.util.RandomUUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.security.crypto.bcrypt.BCrypt.checkpw;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final EmailSenderServiceImpl emailSenderService;

    private final RandomUUID randomValue;

    @Override
    public AuthTokenResponse authenticateAndGetToken(AuthLoginRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (authRequest.identityNumber(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            Auth auth = findByIdentityNumber(authRequest.identityNumber());
            log.info("Identity Number: {} - Login", authRequest.identityNumber());
            return new AuthTokenResponse(auth.getId(),
                    jwtService.generateToken(authRequest.identityNumber()), auth.getRoles());
        }
        throw new RuntimeException("Unexpected Exception");
    }

    @Override
    public String addUserCustomer(AuthRegisterRequest authRegisterRequest) {
        String encodedPassword = passwordEncoder.encode(authRegisterRequest.password());
        Auth auth = Auth.builder()
                .identityNumber(authRegisterRequest.identityNumber())
                .email(authRegisterRequest.email())
                .password(encodedPassword)
                .accountNonLocked(true)
                .roles(RoleType.ROLE_CUSTOMER.toString()).build();
        authRepository.save(auth);
        auth = authRepository.findByIdentityNumber(authRegisterRequest.identityNumber()).orElseThrow(() -> {
            log.warn("Identity Number: {} - User Not Found", authRegisterRequest.identityNumber());
            return new NotFoundException("User Not Found");
        });
        emailSenderService.sendActivationMail(auth);
        return auth.getId();
    }

    @Override
    public String getUserId(String identityNumber) {
        Auth auth = authRepository.findByIdentityNumber(identityNumber).orElseThrow(() -> {
            log.warn("Identity Number: {} - User Not Found", identityNumber);
            return new NotFoundException("User Not Found");
        });
        return auth.getId();
    }

    @Override
    public Auth getAuth(String id) {
        return authRepository.findById(id).orElseThrow(() -> {
            log.warn("Auth Id: {} - User Not Found", id);
            return new NotFoundException("User Not Found");
        });
    }

    @Override
    public Auth addUserEmployee(AuthEmployeeRegister authEmployeeRegister) {
        String password = randomValue.sendRandomValue();
        Auth auth = Auth.builder()
                .identityNumber(authEmployeeRegister.identityNumber())
                .password(passwordEncoder.encode(password))
                .email(authEmployeeRegister.email())
                .roles(RoleType.ROLE_EMPLOYEE.toString())
                .accountNonLocked(true)
                .enabled(true)
                .build();
        authRepository.save(auth);
        emailSenderService.sendRandomPassword(password, auth);
        log.info("Identity Number: {} - Added Employee", authEmployeeRegister.identityNumber());
        return authRepository.findByIdentityNumber(authEmployeeRegister.identityNumber()).orElseThrow(() -> {
            log.warn("Identity Number: {} - User Not Found", authEmployeeRegister.identityNumber());
            return new NotFoundException("User Not Found");
        });
    }

    @Override
    public Auth addUserAdmin(AuthAdminRegister authAdminRegister) {
        String password = randomValue.sendRandomValue();
        Auth auth = Auth.builder()
                .identityNumber(authAdminRegister.identityNumber())
                .password(passwordEncoder.encode(password))
                .email(authAdminRegister.email())
                .roles(RoleType.ROLE_ADMIN.toString())
                .accountNonLocked(true)
                .enabled(true)
                .build();
        authRepository.save(auth);
        emailSenderService.sendRandomPassword(password, auth);
        log.info("Identity Number: {} - Added Admin", authAdminRegister.identityNumber());
        return authRepository.findByIdentityNumber(authAdminRegister.identityNumber()).orElseThrow(() -> {
            log.warn("Identity Number: {} - User Not Found", authAdminRegister.identityNumber());
            return new NotFoundException("User Not Found");
        });
    }

    @Override
    public boolean changePassword(ChangePasswordRequest changePassword) {
        boolean check;
        Auth auth = authRepository.findById(changePassword.authId()).orElseThrow(() -> {
            log.warn("Auth Id: {} - User Not Found", changePassword.authId());
            return new NotFoundException("User Not Found");
        });
        check = checkpw(changePassword.password(), auth.getPassword());
        if (check) {
            auth.setPassword(passwordEncoder.encode(changePassword.newPassword()));
            authRepository.save(auth);
            return true;
        }
        return false;
    }

    @Override
    public void accountActivation(String authId, String verifyCode) {
        Auth auth = getAuth(authId);
        if (emailSenderService.verifyAccount(auth, verifyCode)) {
            auth.setEnabled(true);
            authRepository.save(auth);
        }
    }

    @Override
    public void forgottenPassword(ForgottenPasswordRequest forgottenPasswordRequest) {
        Auth auth = authRepository.findByEmail(forgottenPasswordRequest.email()).orElseThrow(() -> {
            log.warn("Email address: {} - User Not Found", forgottenPasswordRequest.email());
            return new NotFoundException("User Not Found");
        });
        emailSenderService.forgottenPassword(auth);
    }

    @Override
    public void changeForgottenPassword
            (String authId, String verifyCode, ChangeForgottenPasswordRequest changeForgottenPasswordRequest) {
        Auth auth = getAuth(authId);
        if (emailSenderService.changeForgottenPassword(auth, verifyCode)) {
            auth.setPassword(passwordEncoder.encode(changeForgottenPasswordRequest.password()));
            authRepository.save(auth);
        }
    }

    @Override
    public Auth findByIdentityNumber(String identityNumber) {
        return authRepository.findByIdentityNumber(identityNumber).orElseThrow(() -> {
            log.warn("Identity Number: {} - User Not Found", identityNumber);
            return new NotFoundException("User Not Found");
        });
    }

    @Override
    public void deleteUser(String authId){
        authRepository.deleteById(authId);
    }
}
