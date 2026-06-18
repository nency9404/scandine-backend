package com.scandine.service;

import com.scandine.dto.request.MenuItemRequest;
import com.scandine.dto.response.MenuItemResponse;
import com.scandine.entity.MenuItem;
import com.scandine.exception.ResourceNotFoundException;
import com.scandine.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public List<MenuItemResponse> getAllAvailableItems() {
        return menuItemRepository.findByIsAvailableTrue().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<MenuItemResponse> getItemsByCategory(String category) {
        List<MenuItem> items = menuItemRepository.findByCategory(category);

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("No items found for category: " + category);
        }

        return items.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    //Add new Menu item
    public MenuItemResponse addMenuItem(MenuItemRequest request){
        MenuItem item = new MenuItem();
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());
        item.setIsAvailable(request.getIsAvailable());
        return mapToResponse(menuItemRepository.save(item));
    }

    //edit Existing item
    public MenuItemResponse updateMenuItem(Integer id,MenuItemRequest request){
        MenuItem item = menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Menu item not found with id: " + id
        ));
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setCategory(request.getCategory());
        item.setIsAvailable(request.getIsAvailable());
       return mapToResponse(menuItemRepository.save(item));
    }

    //delete item
    public void deleteMenuItem(Integer id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Menu item not found with id: " + id
                ));
        menuItemRepository.delete(item);
    }

    //Toggle availability
    public MenuItemResponse toggleAvailability(Integer id){
        MenuItem item = menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Menu item not found with id: " + id
        ));
        item.setIsAvailable(!item.getIsAvailable());
        return mapToResponse(menuItemRepository.save(item));
    }

    private MenuItemResponse mapToResponse(MenuItem item) {
        return new MenuItemResponse(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getCategory(),
                item.getIsAvailable()
        );
    }
}
