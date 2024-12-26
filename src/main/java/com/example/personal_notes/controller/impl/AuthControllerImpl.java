package com.example.personal_notes.controller.impl;

import com.example.personal_notes.model.User;
import com.example.personal_notes.model.dto.UserDto;
import com.example.personal_notes.service.impl.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl {
    private final AuthServiceImpl authService;

    public AuthControllerImpl(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody UserDto user) {
        return authService.register(user.username(), user.password());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody UserDto user) {
        return authService.login(user.username(), user.password());
    }
}
