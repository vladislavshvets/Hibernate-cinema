package com.dev.cinema.dto.order;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderDtoMapper {
    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setTicketIds(order.getTickets()
                .stream().map(Ticket::getId)
                .collect(Collectors.toList()));
        orderDto.setOrderDate(order.getOrderDate().toString());
        return orderDto;
    }
}
