package com.dev.cinema.service;

import com.dev.cinema.model.User;
import javax.naming.AuthenticationException;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException, javax.security.sasl.AuthenticationException;

    User register(String email, String password);
}
