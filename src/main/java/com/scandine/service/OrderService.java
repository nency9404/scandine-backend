package com.scandine.service;

import com.scandine.dto.request.OrderItemRequest;
import com.scandine.dto.request.PlaceOrderRequest;
import com.scandine.dto.response.OrderItemResponse;
import com.scandine.dto.response.OrderResponse;
import com.scandine.entity.CafeteriaTable;
import com.scandine.entity.MenuItem;
import com.scandine.entity.Order;
import com.scandine.entity.OrderItem;
import com.scandine.enums.OrderStatus;
import com.scandine.exception.InvalidRequestException;
import com.scandine.exception.ResourceNotFoundException;
import com.scandine.repository.CafeteriaTableRepository;
import com.scandine.repository.MenuItemRepository;
import com.scandine.repository.OrderItemRepository;
import com.scandine.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CafeteriaTableRepository tableRepository;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest request) {
        CafeteriaTable table = tableRepository.findByTableNumber(request.getTableNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Table not found with id: " + request.getTableNumber()));

        Order order = new Order();
        order.setCafeteriaTable(table);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalPrice(0.0);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for(OrderItemRequest itemRequest : request.getItems()){
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId()).orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + itemRequest.getMenuItemId()));

            if(!menuItem.getIsAvailable()){
                throw new InvalidRequestException(
                        menuItem.getName() + " is currently unavailable");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.getQuantity());

            orderItems.add(orderItem);

            totalPrice += itemRequest.getQuantity() * menuItem.getPrice();
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponse(order);
    }

    //fetch order with tableId to track status (Customer)
    public List<OrderResponse> getOrdersByTable(Integer tableId){
        tableRepository.findByTableNumber(tableId).orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + tableId));

        List<Order> orders = orderRepository.findByCafeteriaTable_Id(tableId);

        if(orders.isEmpty()){
            throw new ResourceNotFoundException("No orders found for table: " + tableId);
        }

        return orders.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
    }

    //get all active orders (Staff)
    public List<OrderResponse> getAllActiveOrder(){
        List<Order> pending = orderRepository.findByOrderStatus(OrderStatus.PENDING);
        List<Order> preparing = orderRepository.findByOrderStatus(OrderStatus.PREPARING);

        List<Order> active = new ArrayList<>();
        active.addAll(pending);
        active.addAll(preparing);

        return active.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
    }

    //get pending orders (staff)
    public List<OrderResponse> getPendingOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.PENDING)
                .stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    //update order status (Staff)
    public OrderResponse updateOrderStatus(Integer orderId,OrderStatus newStatus){
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order not found with id: " + orderId));

        order.setOrderStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponse(updatedOrder);
    }

    public OrderResponse mapToOrderResponse(Order order){
        List<OrderItemResponse> itemResponses = order.getOrderItems()
                .stream().map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getMenuItem().getName(),
                        item.getMenuItem().getPrice(),
                        item.getQuantity(),
                        item.getQuantity() * item.getMenuItem().getPrice()
                )).collect(Collectors.toList());

        return new OrderResponse(order.getId(),
                                    order.getCafeteriaTable().getId(),
                                    order.getTotalPrice(),
                                    order.getOrderStatus(),
                                    order.getCreatedAt(),
                                    itemResponses
                );
    }


}
