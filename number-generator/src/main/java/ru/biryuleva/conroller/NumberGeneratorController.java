package ru.biryuleva.conroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.biryuleva.dto.NumberDto;
import ru.biryuleva.service.NumberGeneratorService;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
@Tag(name = "number generator", description = "Микросервис генерации номеров заказов")
public class NumberGeneratorController {

    private final NumberGeneratorService service;

    @GetMapping("/number")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Генерация номера заказа", description = "Позволяет создать номер заказа")
    public NumberDto getNumber() {
        log.debug("Получен GET-запрос на получение номера заказа");
        return service.getNumber();
    }
}
