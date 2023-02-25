package com.example.credit.repository;

import com.example.credit.model.Auth;
import com.example.credit.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSenderRepository extends JpaRepository<Mail, String > {
    Mail findByAuth(Auth auth);
}