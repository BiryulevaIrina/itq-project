package ru.biryuleva.itqproject.service;

import ru.biryuleva.itqproject.dto.OrderDetailsDto;
import ru.biryuleva.itqproject.dto.OrderDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderDto create(OrderDto orderDto);

    OrderDto getOrderByMaxAmount(LocalDate date);

    List<Long> getOrders(String name, LocalDate startDate, LocalDate endDate);

    OrderDetailsDto addOrderDetails(OrderDetailsDto detailsDto, Long number);
}
