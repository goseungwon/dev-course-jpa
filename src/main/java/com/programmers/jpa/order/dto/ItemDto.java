package com.programmers.jpa.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDto {
    private Long id;
    private int price;
    private int stockQuantity;

    private ItemType type;

    //food
    private String chef;
    //car
    private Integer power;
    //furniture
    private Integer height;
    private Integer width;
}
