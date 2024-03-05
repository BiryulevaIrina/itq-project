package ru.biryuleva.itqproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.biryuleva.itqproject.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.date = :date ORDER BY o.amount DESC LIMIT 1")
    Optional<Order> findOrderByAmount(LocalDate date);

    @Query("SELECT o.id FROM Order o " +
            "LEFT JOIN OrderDetails d " +
            "WHERE d.itemName != :name " +
            "AND o.date >= :startDate AND o.date <= :endDate")
    List<Long> findOrderIdsExceptProductAndDateRange(String name, LocalDate startDate, LocalDate endDate);

}
