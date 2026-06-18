package com.scandine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter@AllArgsConstructor
public class OrderItemResponse {
    private Integer id;
    private String itemName;
    private Double itemPrice;
    private Integer quantity;
    private Double subTotal;
}
