package ru.biryuleva.itqproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.biryuleva.itqproject.dto.OrderDetailsDto;
import ru.biryuleva.itqproject.dto.OrderDto;
import ru.biryuleva.itqproject.model.Order;
import ru.biryuleva.itqproject.model.OrderDetails;
import ru.biryuleva.itqproject.repository.OrderDetailsRepository;
import ru.biryuleva.itqproject.repository.OrderRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl service;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderDetailsRepository detailsRepository;

    private OrderDto orderDto1;
    private OrderDetailsDto detailsDto1;
    private Order order;

    private OrderDetails details;

    @BeforeEach
    void setUp() {
        LocalDate date = LocalDate.of(2024, Month.MARCH, 4);
        orderDto1 = new OrderDto(1L, 100.0, date, 123654L);
        order = new Order(1L, 100.0, date, 123654L);
        detailsDto1 = new OrderDetailsDto(1L, "Product", 100L, orderDto1);
        details = new OrderDetails(1L, "Product", 100L, order);
    }

    @Test
    void createOrderTest() {

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto orderDto = service.create(orderDto1);

        assertEquals(order.getId(), orderDto.getId());
        assertEquals(order.getAmount(), orderDto.getAmount());
        assertEquals(order.getDate(), orderDto.getDate());
        assertEquals(order.getNumber(), orderDto.getNumber());

    }

    @Test
    void getOrderByMaxAmountTest() {
        Order order1 = new Order(2L, 200.0,
                LocalDate.of(2024, Month.MARCH, 4), 123456L);

        when(orderRepository.findOrderByAmount(any(LocalDate.class))).thenReturn(Optional.of(order1));

        OrderDto orderByMaxAmountDto = service.getOrderByMaxAmount(LocalDate.of(2024, Month.MARCH, 4));

        assertEquals(orderByMaxAmountDto.getId(), 2L);
        assertEquals(orderByMaxAmountDto.getAmount(), 200.0);
        assertEquals(orderByMaxAmountDto.getDate(), LocalDate.of(2024, Month.MARCH, 4));
        assertEquals(orderByMaxAmountDto.getNumber(), 123456L);

        verify(orderRepository, times(1))
                .findOrderByAmount(LocalDate.of(2024, Month.MARCH, 4));
    }

    @Test
    void getOrdersTest() {
        Order order1 = new Order(2L, 200.0,
                LocalDate.of(2024, Month.MARCH, 4), 123456L);
        details = new OrderDetails(1L, "Product", 100L, order1);
        LocalDate startDate = LocalDate.of(2024, Month.MARCH, 2);
        LocalDate endDate = LocalDate.of(2024, Month.MARCH, 5);

        when(orderRepository.findOrderIdsExceptProductAndDateRange(anyString(), any(LocalDate.class),
                any(LocalDate.class)))
                .thenReturn(Collections.singletonList(details.getOrder().getId()));

        List<Long> ids = service.getOrders("ProductA", startDate, endDate);

        assertNotNull(ids);
        assertEquals(1, ids.size());
    }

    @Test
    void addOrderDetailsTest() {
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(order));
        when(detailsRepository.save(any(OrderDetails.class))).thenReturn(details);

        OrderDetailsDto detailsDto = service.addOrderDetails(detailsDto1, 1L);

        assertEquals(details.getId(), detailsDto.getId());
        assertEquals(details.getItemName(), detailsDto.getItemName());
        assertEquals(details.getQuantity(), detailsDto.getQuantity());
        assertNotNull(detailsDto.getOrder());

    }

}