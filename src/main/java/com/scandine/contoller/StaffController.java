package com.scandine.contoller;

import com.scandine.dto.request.MenuItemRequest;
import com.scandine.dto.request.UpdateOrderStatusRequest;
import com.scandine.dto.response.MenuItemResponse;
import com.scandine.dto.response.OrderResponse;
import com.scandine.service.MenuItemService;
import com.scandine.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@Tag(name = "Staff APIs", description = "Kitchen and manager endpoints — JWT required")
public class StaffController {
    private final OrderService orderService;
    private final MenuItemService menuItemService;

    @GetMapping("/orders")
    @Operation(summary = "Get all active orders")
    public ResponseEntity<List<OrderResponse>> getAllActiveOrders(){
        return ResponseEntity.ok(orderService.getAllActiveOrder());
    }

    @GetMapping("/orders/pending")
    @Operation(summary = "Get only pending orders")
    public ResponseEntity<List<OrderResponse>> getPendingOrdres(){
        return ResponseEntity.ok(orderService.getPendingOrder());
    }

    @PutMapping("/orders/{id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Integer id , @Valid @RequestBody UpdateOrderStatusRequest request){
        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, request.getOrderStatus()));
    }

    @PostMapping("/menu")
    @Operation(summary = "Add new menu item")
    public ResponseEntity<MenuItemResponse> addMenuItem(
            @Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuItemService.addMenuItem(request));
    }

    @PutMapping("/menu/{id}")
    @Operation(summary = "Edit existing menu item")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Integer id, @Valid @RequestBody MenuItemRequest request){
        return ResponseEntity.ok(menuItemService.updateMenuItem(id,request));

    }

    @DeleteMapping("/menu/{id}")
    @Operation(summary = "Delete menu item")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Integer id){
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/menu/{id}/toggle")
    @Operation(summary = "Toggle item availability")
    public ResponseEntity<MenuItemResponse> toggleAvailability(@PathVariable Integer id){
        return ResponseEntity.ok(menuItemService.toggleAvailability(id));
    }
}
