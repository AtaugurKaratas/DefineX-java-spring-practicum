package com.definex.credit.service.impl;

import com.definex.credit.service.EmailSenderService;
import com.definex.credit.util.RandomUUID;
import com.definex.credit.util.SendMail;
import com.definex.credit.model.Auth;
import com.definex.credit.model.Mail;
import com.definex.credit.model.enumeration.MailType;
import com.definex.credit.repository.EmailSenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final EmailSenderRepository eMailSenderRepository;

    private final SendMail mailUtil;

    private final RandomUUID randomValue;

    @Override
    public void sendRandomPassword(String password, Auth auth) {
        String email = auth.getEmail();
        String subject = MailType.USER_FIRST_PASSWORD.toString();
        String body = MessageFormat.format("{0}", password);
        mailUtil.sendEmail(email, subject, body);
        Mail mail = new Mail();
        mail.setAuth(auth);
        mail.setMailType(MailType.USER_FIRST_PASSWORD);
        mail.setMessage(password);
        eMailSenderRepository.save(mail);
    }

    @Override
    public void sendActivationMail(Auth auth) {
        String email = auth.getEmail();
        String subject = MailType.ACCOUNT_ACTIVATION.toString();
        String verifyCode = randomValue.sendRandomValue();
        String body = MessageFormat.format("http://localhost:3000/#/auth/{0}/{1}", auth.getId(), verifyCode);
        mailUtil.sendEmail(email, subject, body);
        Mail mail = new Mail();
        mail.setAuth(auth);
        mail.setMailType(MailType.ACCOUNT_ACTIVATION);
        mail.setMessage(verifyCode);
        eMailSenderRepository.save(mail);
    }


    @Override
    public boolean verifyAccount(Auth auth, String verifyCode) {
        Mail mail = eMailSenderRepository.findByAuth(auth);
        if (verifyCode.equals(mail.getMessage())) {
            mail.setActive(true);
            eMailSenderRepository.save(mail);
            return true;
        }
        return false;
    }

    @Override
    public void forgottenPassword(Auth auth) {
        String email = auth.getEmail();
        String subject = MailType.FORGOTTEN_PASSWORD.toString();
        String verifyCode = randomValue.sendRandomValue();
        String body = MessageFormat.format("http://localhost:3000/#/auth/forgottenPassword/{0}/{1}", auth.getId(), verifyCode);
        mailUtil.sendEmail(email, subject, body);
        Mail mail = new Mail();
        mail.setAuth(auth);
        mail.setMailType(MailType.FORGOTTEN_PASSWORD);
        mail.setMessage(verifyCode);
        eMailSenderRepository.save(mail);
    }

    @Override
    public boolean changeForgottenPassword(Auth auth, String verifyCode) {
        Mail mail = eMailSenderRepository.findByAuth(auth);
        if (verifyCode.equals(mail.getMessage())) {
            mail.setActive(true);
            eMailSenderRepository.save(mail);
            return true;
        }
        return false;
    }
}