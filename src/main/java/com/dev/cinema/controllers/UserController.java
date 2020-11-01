package com.dev.cinema.controllers;

import com.dev.cinema.dto.user.UserDtoMapper;
import com.dev.cinema.dto.user.UserResponseDto;
import com.dev.cinema.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserDtoMapper userMapper;
    private final UserService userService;

    public UserController(UserDtoMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/by-email")
    public UserResponseDto findByEmail(@RequestParam String email) {
        return userMapper.mapToDto(userService.findByEmail(email).get());
    }
}
