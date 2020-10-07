package com.dev.cinema.service.impl;

import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import javax.naming.AuthenticationException;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public User login(String email, String password) throws AuthenticationException {
        return null;
    }

    @Override
    public User register(String email, String password) {
        return null;
    }
}
