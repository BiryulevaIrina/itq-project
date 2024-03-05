package ru.biryuleva.itqproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private Long id;
    @NotBlank
    private String itemName;
    @NotBlank
    private Long quantity;
    @NotBlank
    private OrderDto order;
}
