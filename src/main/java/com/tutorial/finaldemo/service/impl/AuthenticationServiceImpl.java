package com.tutorial.finaldemo.service.impl;

import com.tutorial.finaldemo.dto.JwtAuthenticationResponse;
import com.tutorial.finaldemo.dto.RefreshTokenRequest;
import com.tutorial.finaldemo.dto.SignInRequest;
import com.tutorial.finaldemo.dto.SignUpRequest;
import com.tutorial.finaldemo.entity.Role;
import com.tutorial.finaldemo.entity.User;
import com.tutorial.finaldemo.reponsitory.RoleReponsitory;
import com.tutorial.finaldemo.reponsitory.UserReponsitory;
import com.tutorial.finaldemo.service.AuthenticationService;
import com.tutorial.finaldemo.service.JwtService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(MyTimerTask.class);
    private final RoleReponsitory roleReponsitory;
    private final UserReponsitory userReponsitory;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("sendEmail")
    private Job job;
    @Autowired
    private JavaMailSender javaMailSender;

    public static String generateRandomCode(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public User signUp(SignUpRequest signUpRequest, String siteURL) throws MessagingException, UnsupportedEncodingException {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        Set<Role> roles = new HashSet<Role>();
        Role defaultRole = roleReponsitory.findByName("USER").orElseThrow(() -> new IllegalArgumentException("Role not found with name"));
        roles.add(defaultRole);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        String randomCode = generateRandomCode(64);
        user.setVerificationCode(randomCode);
        user.setVerified(false);
        user.setRegistrationTime(LocalDateTime.now());
        sendVerificationEmail(user, siteURL);
        schedule();
        return userReponsitory.save(user);
    }

    public void schedule() {
        Timer timer = new Timer();
        long delayInMillis = 30000;
        timer.schedule(new MyTimerTask(), delayInMillis);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        var user = userReponsitory.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userReponsitory.findByEmail(userEmail).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;

        }
        return null;
    }

    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "vanducpro711@gmail.com";
        String senderName = "vanduc";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/auth/signup/" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);

    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters();

                JobExecution jobExecution = jobLauncher.run(job, jobParameters);

                if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                    logger.info("Batch Job Completed Successfully");
                } else {
                    logger.error("Batch Job Failed with Status: " + jobExecution.getStatus());
                }
            } catch (Exception e) {
                logger.error("Error running batch job", e);
            }
            logger.info("Delayed task executed!");
        }
    }
}
