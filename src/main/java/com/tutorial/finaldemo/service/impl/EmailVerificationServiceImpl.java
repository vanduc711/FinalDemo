package com.tutorial.finaldemo.service.impl;

import com.tutorial.finaldemo.entity.User;
import com.tutorial.finaldemo.service.EmailVerificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public User sendEmailNotification(String to, String subject, String content) {
        System.out.println("Sending deletion email to: " + to);
        String fromAddress = "vanducpro711@gmail.com";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);  // Set true để hỗ trợ HTML content

            javaMailSender.send(mimeMessage);
            System.out.println("Deletion email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();  // Xử lý lỗi khi gửi email
            System.out.println("Failed to send deletion email!");
        }
        return null;
    }
}