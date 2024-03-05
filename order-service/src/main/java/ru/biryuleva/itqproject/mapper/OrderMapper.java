package ru.biryuleva.itqproject.mapper;

import org.springframework.stereotype.Component;
import ru.biryuleva.itqproject.dto.OrderDto;
import ru.biryuleva.itqproject.model.Order;


@Component
public class OrderMapper {

    public static OrderDto toOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getAmount(),
                order.getDate(),
                order.getNumber()
        );
    }

    public static Order mapToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setNumber(orderDto.getNumber());
        order.setAmount(orderDto.getAmount());
        order.setDate(orderDto.getDate());
        return order;
    }
}
