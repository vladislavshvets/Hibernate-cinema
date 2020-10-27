package com.dev.cinema.dto.shoppingCart;

import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ShoppingCartDtoMapper {
    public ShoppingCartResponseDto mapToDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto cartDto = new ShoppingCartResponseDto();
        cartDto.setId(shoppingCart.getId());
        cartDto.setTicketIds(shoppingCart.getTickets()
                .stream().map(Ticket::getId)
                .collect(Collectors.toList()));
        return cartDto;
    }
}
