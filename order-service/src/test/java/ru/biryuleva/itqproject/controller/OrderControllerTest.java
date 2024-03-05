package ru.biryuleva.itqproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.biryuleva.itqproject.dto.OrderDetailsDto;
import ru.biryuleva.itqproject.dto.OrderDto;
import ru.biryuleva.itqproject.service.OrderService;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private OrderService service;
    @Autowired
    private MockMvc mockMvc;
    private OrderDto dto;
    private OrderDetailsDto detailsDto;

    @BeforeEach
    void setUp() {
        dto = new OrderDto(1L, 100.0, LocalDate.now(), 123456L);
        detailsDto = new OrderDetailsDto(1L, "Product", 100L, dto);
    }

    @Test
    void createNewOrderTest() throws Exception {
        when(service.create(any())).thenReturn(dto);
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void createOrderDetailsTest() throws Exception {
        when(service.addOrderDetails(detailsDto, 1L)).thenReturn(detailsDto);

        mockMvc.perform(post("/orders/1/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getOrderByMaxAmountTest() throws Exception {
        OrderDto dto1 = new OrderDto(2L, 200.0,
                LocalDate.of(2024, Month.MARCH, 4), 123456L);
        when(service.getOrderByMaxAmount(any(LocalDate.class))).thenReturn(dto1);

        MvcResult result = mockMvc.perform(get("/orders/maxAmount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", "2024-03-04"))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
        verify(service, Mockito.times(1)).getOrderByMaxAmount(LocalDate.of(2024, Month.MARCH, 4));
    }

    @Test
    void getOrdersTest() throws Exception {
        LocalDate startDate = LocalDate.of(2024, Month.MARCH, 2);
        LocalDate endDate = LocalDate.of(2024, Month.MARCH, 5);
        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "ProductA")
                        .param("startDate", "2024-03-02")
                        .param("endDate", "2024-03-05"))
                .andExpect(status().isOk());

        verify(service, Mockito.times(1)).getOrders("ProductA", startDate, endDate);
    }
}