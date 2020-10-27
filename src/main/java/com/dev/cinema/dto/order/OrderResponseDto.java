package com.dev.cinema.dto.order;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private String orderDate;
    private List<Long> ticketIds;
}
