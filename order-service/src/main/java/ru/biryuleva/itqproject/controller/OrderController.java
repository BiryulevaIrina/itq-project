package ru.biryuleva.itqproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.biryuleva.itqproject.dto.OrderDetailsDto;
import ru.biryuleva.itqproject.dto.OrderDto;
import ru.biryuleva.itqproject.service.OrderService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "itq project", description = "Управляет заказами")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание заказа", description = "Позволяет создать заказ")
    public OrderDto createNewOrder(@Valid @RequestBody OrderDto orderDto) {
        log.info("Получен POST-запрос на добавление заказа");
        return orderService.create(orderDto);
    }

    @PostMapping("/orders/{id}/details")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание деталей заказов", description = "Позволяет создать детали заказа")
    public OrderDetailsDto createOrderDetails(@RequestBody OrderDetailsDto detailsDto,
                                              @Min(1) @PathVariable Long id) {
        log.info("Получен POST-запрос на добавление деталей к заказу с id={}", id);
        return orderService.addOrderDetails(detailsDto, id);
    }

    @GetMapping("/orders/maxAmount")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение заказа", description = "Позволяет получить информацию о заказе с мах суммой")
    public OrderDto getOrderByMaxAmount(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                        @RequestParam LocalDate date) {
        log.info("Получен GET-запрос на получение заказа c максимальной суммой в заданную дату {}", date);
        return orderService.getOrderByMaxAmount(date);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка номеров заказов", description = "Позволяет получить список номеров заказов, " +
            "не содержащих заданный товар и пступивших в заданный период")
    public List<Long> getOrders(@RequestParam(required = false) String name,
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                @RequestParam(required = false) LocalDate startDate,
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                @RequestParam(required = false) LocalDate endDate) {
        log.info("Получен GET-запрос на получение списка id заказов, не содержащих товар - {} и поступивших " +
                "в период с {} по {}", name, startDate, endDate);
        return orderService.getOrders(name, startDate, endDate);
    }

}
