package com.tutorial.finaldemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ResponseEntity<?> sayHello() {
        return ResponseEntity.ok("Hello user");
    }


}
