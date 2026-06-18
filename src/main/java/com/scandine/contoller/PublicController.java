package com.scandine.contoller;

import com.scandine.dto.request.PlaceOrderRequest;
import com.scandine.dto.response.MenuItemResponse;
import com.scandine.dto.response.OrderResponse;
import com.scandine.service.MenuItemService;
import com.scandine.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor //for only final fields
@RequestMapping("/api/v1/public")
@Tag(name = "Public APIs", description = "Customer facing endpoints — no auth required")
public class PublicController {

    private final MenuItemService menuItemService;
    private final OrderService orderService;

    @GetMapping("/menu")
    @Operation(summary = "Get all available menu items")
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems(){
        return ResponseEntity.ok(menuItemService.getAllAvailableItems());
    }

    @GetMapping("/menu/category/{category}")
    @Operation(summary = "Get menu items by category")
    public ResponseEntity<List<MenuItemResponse>> getItemsByCategory(@PathVariable String category){
        return ResponseEntity.ok(menuItemService.getItemsByCategory(category));
    }

    @PostMapping("/orders/place")
    @Operation(summary = "Place a new order")
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(request));
    }

    @GetMapping("/orders/{tableId}/status")
    @Operation(summary = "get order status")
    public ResponseEntity<List<OrderResponse>> getOrderStatus(@PathVariable Integer tableId){
            return ResponseEntity.ok(orderService.getOrdersByTable(tableId));
    }
}
