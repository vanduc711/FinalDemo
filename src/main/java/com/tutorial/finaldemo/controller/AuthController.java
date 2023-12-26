package com.tutorial.finaldemo.controller;

import com.tutorial.finaldemo.dto.JwtAuthenticationResponse;
import com.tutorial.finaldemo.dto.RefreshTokenRequest;
import com.tutorial.finaldemo.dto.SignInRequest;
import com.tutorial.finaldemo.dto.SignUpRequest;
import com.tutorial.finaldemo.entity.User;
import com.tutorial.finaldemo.reponsitory.UserReponsitory;
import com.tutorial.finaldemo.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    private final UserReponsitory userReponsitory;

    @SneakyThrows
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        authenticationService.signUp(signUpRequest, getSiteURL(request));
        return ResponseEntity.ok("register_success");
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/signup/{code}")
    public ResponseEntity<String> verifyUser(@PathVariable("code") String code) {
        User user = userReponsitory.findByVerificationCode(code);
        user.setVerified(true);
        userReponsitory.save(user);
        return ResponseEntity.ok("susscessful");
    }
}