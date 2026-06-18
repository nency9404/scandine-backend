package com.scandine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MenuItemResponse {
    private Integer id;
    private String name;
    private Double price;
    private String category;
    private boolean isAvailable;
}
