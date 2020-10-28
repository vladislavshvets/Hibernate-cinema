package com.dev.cinema.dto.shoppingcart;

import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

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
