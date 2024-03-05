package ru.biryuleva.itqproject.mapper;

import org.springframework.stereotype.Component;
import ru.biryuleva.itqproject.dto.OrderDetailsDto;
import ru.biryuleva.itqproject.dto.OrderDto;
import ru.biryuleva.itqproject.model.OrderDetails;


@Component
public class OrderDetailsMapper {

    public static OrderDetailsDto mapToOrderDetailsDto(OrderDetails orderDetails, OrderDto orderDto) {
        OrderDetailsDto detailsDto = new OrderDetailsDto();
        detailsDto.setId(orderDetails.getId());
        detailsDto.setItemName(orderDetails.getItemName());
        detailsDto.setQuantity(orderDetails.getQuantity());
        detailsDto.setOrder(orderDto);
        return detailsDto;
    }

    public static OrderDetails mapToOrderDetails(OrderDetailsDto detailsDto) {
        OrderDetails details = new OrderDetails();
        details.setId(detailsDto.getId());
        details.setItemName(detailsDto.getItemName());
        details.setQuantity(detailsDto.getQuantity());
        return details;
    }
}
