package com.definex.credit.repository;

import com.definex.credit.model.Auth;
import com.definex.credit.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSenderRepository extends JpaRepository<Mail, String > {
    Mail findByAuth(Auth auth);
}