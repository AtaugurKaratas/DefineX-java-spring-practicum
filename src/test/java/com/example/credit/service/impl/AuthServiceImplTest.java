package com.example.credit.service.impl;

import com.example.credit.dto.request.*;
import com.example.credit.exception.NotFoundException;
import com.example.credit.model.Auth;
import com.example.credit.model.enumeration.RoleType;
import com.example.credit.repository.AuthRepository;
import com.example.credit.security.JwtService;
import com.example.credit.util.RandomUUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailSenderServiceImpl emailSenderService;

    @Mock
    private RandomUUID randomValue;

    @Test
    void ShouldReturnUserIdWhenIdentityNumberIsCorrect() {
        String identityNumber = "12345678901";
        Auth auth = new Auth("id"
                , "12345678901"
                , "ataugurkaratas@gmail.com"
                , "password"
                , "ROLE_ADMIN"
                , true
                , true);
        when(authRepository.findByIdentityNumber(identityNumber)).thenReturn(Optional.of(auth));
        authService.getUserId(identityNumber);
    }

    @Test
    void ShouldReturnAuth() {
        String id = "id";
        Auth auth = new Auth(id
                , "12345678901"
                , "ataugurkaratas@gmail.com"
                , "password"
                , "ROLE_ADMIN"
                , true
                , true);
        when(authRepository.findById(id)).thenReturn(Optional.of(auth));
        authService.getAuth(id);
    }

    @Test
    void ShouldAddUserEmployeeWhenInputsAreCorrect() {
        AuthEmployeeRegister authEmployeeRegister = new AuthEmployeeRegister("12345678901"
                , "Ataugur"
                , "Karatas"
                , "ataugurkaratas@gmail.com"
                , LocalDate.of(1998, 6, 4)
                , "905405404040");

        String password = "password";

        when(randomValue.sendRandomValue()).thenReturn(password);
        Auth auth = Auth.builder()
                .identityNumber(authEmployeeRegister.identityNumber())
                .password(passwordEncoder.encode(password))
                .email(authEmployeeRegister.email())
                .roles(RoleType.ROLE_EMPLOYEE.toString())
                .accountNonLocked(true)
                .enabled(true)
                .build();
        when(authRepository.findByIdentityNumber("12345678901")).thenReturn(Optional.of(auth));
        authService.addUserEmployee(authEmployeeRegister);
    }

    @Test
    void ShouldAddUserEmployeeWhenInputsAreCorrect2() {
        AuthEmployeeRegister authEmployeeRegister = new AuthEmployeeRegister("12345678901"
                , "Ataugur"
                , "Karatas"
                , "ataugurkaratas@gmail.com"
                , LocalDate.of(1998, 6, 4)
                , "905405404040");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                authService.addUserEmployee(authEmployeeRegister));

        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void ShouldChangePasswordWhenThrownException() {
        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("id", "password", "newPassword");
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                authService.changePassword(changePasswordRequest));

        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void ShouldAccountActivation() {
        String authId = "id";
        String verifyCode = "verifyCode";
        Auth auth = Auth.builder()
                .identityNumber("12345678901")
                .password(passwordEncoder.encode("password"))
                .email("ataugurkaratas@gmail.com")
                .roles(RoleType.ROLE_EMPLOYEE.toString())
                .accountNonLocked(true)
                .enabled(true)
                .build();
        auth.setId(authId);
        when(authRepository.findById(authId)).thenReturn(Optional.of(auth));
        when(emailSenderService.verifyAccount(auth, verifyCode)).thenReturn(true);
        authService.accountActivation(authId, verifyCode);
        verify(authRepository).save(auth);
    }

    @Test
    void ShouldForgottenPassword() {
        ForgottenPasswordRequest forgottenPasswordRequest =
                new ForgottenPasswordRequest("ataugurkaratas@gmail.com");
        Auth auth = mock(Auth.class);
        when(authRepository.findByEmail(forgottenPasswordRequest.email())).thenReturn(Optional.of(auth));
        authService.forgottenPassword(forgottenPasswordRequest);

        verify(emailSenderService).forgottenPassword(auth);
    }

    @Test
    void ShouldForgottenPasswordWhenThrowException() {
        ForgottenPasswordRequest forgottenPasswordRequest =
                new ForgottenPasswordRequest("ataugurkaratas@gmail.com");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                authService.forgottenPassword(forgottenPasswordRequest));

        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void ShouldChangeForgottenPassword() {
        Auth auth = Auth.builder()
                .identityNumber("12345678901")
                .password(passwordEncoder.encode("password"))
                .email("ataugurkaratas@gmail.com")
                .roles(RoleType.ROLE_EMPLOYEE.toString())
                .accountNonLocked(true)
                .enabled(true)
                .build();
        auth.setId("id");
        String verifyCode = "code";
        ChangeForgottenPasswordRequest passwordRequest =
                new ChangeForgottenPasswordRequest("newPassword");

        when(authRepository.findById(auth.getId())).thenReturn(Optional.of(auth));
        when(emailSenderService.changeForgottenPassword(auth, verifyCode)).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodePassword");
        auth.setPassword(passwordEncoder.encode("newPassword"));

        authService.changeForgottenPassword("id", "code", passwordRequest);

        verify(authRepository, times(1)).findById("id");
        verify(emailSenderService, times(1)).changeForgottenPassword(auth, verifyCode);
        verify(authRepository, times(1)).save(auth);
        assertThat(auth.getPassword()).isEqualTo("encodePassword");
    }

    @Test
    void ShouldFindByIdentityNumber() {
        String identityNumber = "12345678901";
        Auth auth = new Auth();
        when(authRepository.findByIdentityNumber(identityNumber)).thenReturn(Optional.of(auth));
        authService.findByIdentityNumber(identityNumber);

        verify(authRepository).findByIdentityNumber(identityNumber);
    }

    @Test
    void ShouldThrowExceptionWhenFindByIdentityNumber() {
        String identityNumber = "12345678901";

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                authService.findByIdentityNumber(identityNumber));

        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void ShouldChangePasswordWhenInputsAreCorrect() {
        ChangePasswordRequest request = new ChangePasswordRequest("authId"
                , "password"
                , "newPassword");

        Auth auth = new Auth();
        auth.setPassword("$2a$12$LKQ5GSXpZBgHjnMB817pB.QE0b/BjhNkuWxb7QsX9grNCgtYhh4K2");

        when(authRepository.findById(request.authId())).thenReturn(Optional.of(auth));

        boolean check = authService.changePassword(request);

        assertTrue(check);
    }

    @Test
    void ShouldChangePasswordWhenInputsAreNotTrue() {
        ChangePasswordRequest request = new ChangePasswordRequest("authId"
                , "pass"
                , "newPassword");

        Auth auth = new Auth();
        auth.setPassword("$2a$12$LKQ5GSXpZBgHjnMB817pB.QE0b/BjhNkuWxb7QsX9grNCgtYhh4K2");

        when(authRepository.findById(request.authId())).thenReturn(Optional.of(auth));

        boolean check = authService.changePassword(request);

        assertFalse(check);
    }

    @Test
    void ShouldThrowExceptionWhenAddUserAdmin(){
        AuthAdminRegister authAdminRegister = new AuthAdminRegister("11111111111"
        ,"Ataugur"
        ,"Karatas"
        ,"ataugurkaratas@gmail.com"
        ,LocalDate.of(1998,6,4)
        ,"905405404040");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                authService.addUserAdmin(authAdminRegister));

        assertEquals("User Not Found", exception.getMessage());
    }
}