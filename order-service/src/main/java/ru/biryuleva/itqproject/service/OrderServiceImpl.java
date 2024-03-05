package ru.biryuleva.itqproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.biryuleva.itqproject.client.Client;
import ru.biryuleva.itqproject.dto.NumberDto;
import ru.biryuleva.itqproject.dto.OrderDetailsDto;
import ru.biryuleva.itqproject.dto.OrderDto;
import ru.biryuleva.itqproject.exception.NotFoundException;
import ru.biryuleva.itqproject.mapper.OrderDetailsMapper;
import ru.biryuleva.itqproject.mapper.OrderMapper;
import ru.biryuleva.itqproject.model.Order;
import ru.biryuleva.itqproject.model.OrderDetails;
import ru.biryuleva.itqproject.repository.OrderDetailsRepository;
import ru.biryuleva.itqproject.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository detailsRepository;
    private final Client client;

    @Override
    public OrderDto create(OrderDto orderDto) {
        NumberDto number = client.getNumber();
        orderDto.setNumber(number.getNumber());
        orderDto.setDate(LocalDate.now());
        return OrderMapper.toOrderDto(orderRepository.save(OrderMapper.mapToOrder(orderDto)));
    }

    @Override
    public OrderDto getOrderByMaxAmount(LocalDate date) {
        return orderRepository.findOrderByAmount(date)
                .map(OrderMapper::toOrderDto)
                .orElseThrow(() -> new NotFoundException("Заказа на дату " + date
                        + " нет в базе."));
    }

    @Override
    public List<Long> getOrders(String name, LocalDate startDate, LocalDate endDate) {
        return orderRepository.findOrderIdsExceptProductAndDateRange(name, startDate,
                endDate);
    }

    @Override
    public OrderDetailsDto addOrderDetails(OrderDetailsDto detailsDto, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказа c номером " + id
                        + " нет в базе."));
        OrderDetails details = OrderDetailsMapper.mapToOrderDetails(detailsDto);
        details.setOrder(order);
        return OrderDetailsMapper.mapToOrderDetailsDto(detailsRepository.save(details), OrderMapper.toOrderDto(order));
    }

}
