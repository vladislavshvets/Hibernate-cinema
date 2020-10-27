package com.dev.cinema.dto.shoppingCart;

import lombok.Data;
import java.util.List;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private List<Long> ticketIds;
}
