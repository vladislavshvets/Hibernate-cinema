package com.dev.cinema.controllers;

import com.dev.cinema.dto.order.OrderDtoMapper;
import com.dev.cinema.dto.order.OrderResponseDto;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderDtoMapper orderMapper;
    private final OrderService orderService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderDtoMapper orderMapper,
                           OrderService orderService,
                           UserService userService,
                           ShoppingCartService shoppingCartService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/complete")
    public void complete(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByEmail(userEmail).get();
        List<Ticket> tickets = shoppingCartService.getByUser(user).getTickets();
        orderService.completeOrder(tickets, user);
    }

    @GetMapping
    public List<OrderResponseDto> getOrder(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        return orderService.getOrderHistory(userService.findByEmail(userEmail).get()).stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
