package com.programmers.jpa.order.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private int price;
    private int quantity;

    private List<ItemDto> itemDtos;
}
