package com.dev.cinema.dto.order;

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
