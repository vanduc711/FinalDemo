package com.tutorial.finaldemo.service;

import com.tutorial.finaldemo.entity.User;

public interface EmailVerificationService {
    User sendEmailNotification(String to, String subject, String content);
}
