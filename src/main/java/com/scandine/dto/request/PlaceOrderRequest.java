package com.scandine.dto.request;

import com.scandine.entity.MenuItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest {

    @NotNull(message = "Table ID is required")
    private Integer tableNumber;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    List<OrderItemRequest> items;
}
