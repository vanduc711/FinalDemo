package com.tutorial.finaldemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping(value = "/home")
    public ResponseEntity<?> sayHello() {
        return ResponseEntity.ok("Hello admin");
    }

}
