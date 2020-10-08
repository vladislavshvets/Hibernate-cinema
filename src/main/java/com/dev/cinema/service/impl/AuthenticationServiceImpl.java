package com.dev.cinema.service.impl;

import com.dev.cinema.lib.Inject;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws ArithmeticException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && isPasswordValid(password, userFromDB.get())) {
            return userFromDB.get();
        }
        throw new ArithmeticException("Incorrect email or password");
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
    public boolean isPasswordValid(String psw, User user) {
        return HashUtil.hashPassword(psw, user.getSalt()).equals(user.getPassword());
    }
}
