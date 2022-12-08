package com.programmers.jpa.order.dto;

import com.programmers.jpa.domain.order.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String uuid;
    private String memo;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;

    private MemberDto memberDto;
    private List<OrderItemDto> orderItemDtos;
}
