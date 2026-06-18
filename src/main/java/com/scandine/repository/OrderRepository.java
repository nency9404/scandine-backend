package com.scandine.repository;

import com.scandine.entity.Order;
import com.scandine.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCafeteriaTable_Id(Integer tableId);
    List<Order> findByOrderStatus(OrderStatus status);
}
