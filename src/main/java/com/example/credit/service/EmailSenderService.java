package com.example.credit.service;

import com.example.credit.model.Auth;

public interface EmailSenderService {
    void sendRandomPassword(String password, Auth auth);

    void sendActivationMail(Auth auth);

    boolean verifyAccount(Auth auth, String verifyCode);

    boolean changeForgottenPassword(Auth auth, String verifyCode);

    void forgottenPassword(Auth auth);
}
