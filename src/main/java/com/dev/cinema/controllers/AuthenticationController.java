package com.dev.cinema.controllers;

import com.dev.cinema.service.AuthenticationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public void register(@RequestBody @Valid UserRegistrationDto dto) {
        service.register(dto.getEmail(), dto.getPassword());
    }
}
