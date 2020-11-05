package com.dev.cinema.service.impl;

import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.RoleService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final RoleService roleService;

    public AuthenticationServiceImpl(UserService userService,
        ShoppingCartService shoppingCartService,
        RoleService roleService) {
            this.userService = userService;
            this.shoppingCartService = shoppingCartService;
            this.roleService = roleService;
        }

        @Override
        public User register(String email, String password) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setRoles(Set.of(roleService.getRoleByName("USER")));
            userService.add(user);
            shoppingCartService.registerNewShCart(user);
            return user;
        }
}