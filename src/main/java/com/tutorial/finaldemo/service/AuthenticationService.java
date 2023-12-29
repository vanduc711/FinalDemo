package com.tutorial.finaldemo.service;

import com.tutorial.finaldemo.dto.JwtAuthenticationResponse;
import com.tutorial.finaldemo.dto.RefreshTokenRequest;
import com.tutorial.finaldemo.dto.SignInRequest;
import com.tutorial.finaldemo.dto.SignUpRequest;
import com.tutorial.finaldemo.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface AuthenticationService {
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    User signUp(SignUpRequest signUpRequest, String siteURL) throws MessagingException, UnsupportedEncodingException;

}
