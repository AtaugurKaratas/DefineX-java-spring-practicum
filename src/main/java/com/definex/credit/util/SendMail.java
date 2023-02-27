package com.definex.credit.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SendMail {

    private final JavaMailSender mailSender;

    @Value("mail-address")
    private String email;

    public SendMail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail,
                          String subject,
                          String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        try {
            mailSender.send(message);
        } catch (MailAuthenticationException exception)
        {
            log.error("E-mail sending error {} - toEmail {}", exception, toEmail);
        }
    }
}
