package ru.biryuleva.itqproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.biryuleva.itqproject.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

}
