package com.scandine.dto.response;

import com.scandine.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private Integer id;
    private Integer tableNumber;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
