package com.example.personal_notes.controller.impl;

import com.example.personal_notes.model.User;
import com.example.personal_notes.model.dto.UserDto;
import com.example.personal_notes.service.impl.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl {
    private final AuthServiceImpl authService;

    public AuthControllerImpl(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody UserDto userDto) {
        User user = authService.register(userDto.username(), userDto.password());
        if (user == null) throw new ResponseStatusException(CONFLICT, "User with provided username already exists!");
        return user;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody UserDto userDto) {
        try {
            return authService.login(userDto.username(), userDto.password());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(UNAUTHORIZED, e.getMessage());
        }
    }
}
