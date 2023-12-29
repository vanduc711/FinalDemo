package com.tutorial.finaldemo.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Objects;

public interface JwtService {

    String generateRefreshToken(Map<String, Objects> extractClaims, UserDetails userDetails);

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
