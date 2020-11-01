package com.dev.cinema.controllers;

import com.dev.cinema.dto.shoppingcart.ShoppingCartDtoMapper;
import com.dev.cinema.dto.shoppingcart.ShoppingCartResponseDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.User;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartDtoMapper shoppingCartMapping;
    private final ShoppingCartService shoppingCartService;
    private final MovieSessionService movieSessionService;
    private final UserService userService;

    public ShoppingCartController(
            ShoppingCartDtoMapper shoppingCartMapping, ShoppingCartService shoppingCartService,
            MovieSessionService movieSessionService, UserService userService) {
        this.shoppingCartMapping = shoppingCartMapping;
        this.shoppingCartService = shoppingCartService;
        this.movieSessionService = movieSessionService;
        this.userService = userService;
    }

    @PostMapping("/movie-session")
    public void addMovieSession(@RequestParam Long movieSessionId, Authentication authentication) {
        MovieSession movieSession = movieSessionService.getById(movieSessionId);
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByEmail(userEmail).get();
        shoppingCartService.addSession(movieSession, user);
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUser(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByEmail(userEmail).get();
        return shoppingCartMapping
                .mapToDto(shoppingCartService.getByUser(user));
    }
}
