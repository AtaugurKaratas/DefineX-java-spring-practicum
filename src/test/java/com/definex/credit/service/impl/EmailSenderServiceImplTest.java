package com.definex.credit.service.impl;

import com.definex.credit.util.RandomUUID;
import com.definex.credit.util.SendMail;
import com.definex.credit.model.Auth;
import com.definex.credit.model.Mail;
import com.definex.credit.repository.EmailSenderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {

    @InjectMocks
    private EmailSenderServiceImpl emailSenderService;

    @Mock
    private EmailSenderRepository eMailSenderRepository;

    @Mock
    private SendMail mailUtil;

    @Mock
    private RandomUUID randomValue;

    @Test
    void ShouldSendRandomPassword() {
        String password = "password";
        Auth auth = new Auth();
        auth.setEmail("ataugurkaratas@gmail.com");
        emailSenderService.sendRandomPassword(password, auth);
        verify(eMailSenderRepository).save(any(Mail.class));
    }

    @Test
    void ShouldSendActivationMail() {
        Auth auth = new Auth();
        auth.setEmail("ataugurkaratas@gmail.com");
        emailSenderService.sendActivationMail(auth);
        verify(eMailSenderRepository).save(any(Mail.class));
    }

    @Test
    void ShouldReturnTrueWhenVerifyCode() {
        Auth auth = new Auth();
        String verifyCode = "VerifyCode";
        Mail mail = new Mail();
        mail.setMessage("VerifyCode");
        when(eMailSenderRepository.findByAuth(auth)).thenReturn(mail);
        emailSenderService.verifyAccount(auth, verifyCode);
        verify(eMailSenderRepository).save(mail);
    }

    @Test
    void ShouldReturnFalseWhenVerifyCode() {
        Auth auth = new Auth();
        String verifyCode = "VerifyCode";
        Mail mail = new Mail();
        mail.setMessage("NotVerifyCode");
        when(eMailSenderRepository.findByAuth(auth)).thenReturn(mail);
        boolean result = emailSenderService.verifyAccount(auth, verifyCode);
        assertFalse(result);
    }

    @Test
    void ShouldForgottenPassword() {
        Auth auth = new Auth();
        auth.setEmail("ataugurkaratas@gmail.com");
        emailSenderService.forgottenPassword(auth);
        verify(eMailSenderRepository).save(any(Mail.class));
    }

    @Test
    void ShouldReturnTrueWhenChangeForgottenPassword() {
        Auth auth = new Auth();
        String verifyCode = "Code";
        Mail mail = new Mail();
        mail.setMessage("Code");
        when(eMailSenderRepository.findByAuth(auth)).thenReturn(mail);
        emailSenderService.changeForgottenPassword(auth, verifyCode);
        verify(eMailSenderRepository).save(mail);
    }

    @Test
    void ShouldReturnFalseWhenChangeForgottenPassword() {
        Auth auth = new Auth();
        String verifyCode = "Code";
        Mail mail = new Mail();
        mail.setMessage("NotCode");
        when(eMailSenderRepository.findByAuth(auth)).thenReturn(mail);
        boolean result = emailSenderService.changeForgottenPassword(auth, verifyCode);
        assertFalse(result);
    }
}