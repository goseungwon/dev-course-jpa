package com.programmers.jpa.order.controller;

import com.programmers.jpa.order.ApiResponse;
import com.programmers.jpa.order.dto.OrderDto;
import com.programmers.jpa.order.service.OrderService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler (Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @GetMapping("/orders/{uuid}")
    public ApiResponse<OrderDto> getOne(
            @PathVariable String uuid
    ) throws NotFoundException {
        OrderDto one = orderService.findOne(uuid);
        return ApiResponse.ok(one);
    }

    @GetMapping("/orders")
    public ApiResponse<Page<OrderDto>> getAll(
            Pageable pageable
    ) {
        Page<OrderDto> all = orderService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @PostMapping("/orders")
    public ApiResponse<String> save(
            @RequestBody OrderDto orderDto
            ) {
        String uuid = orderService.save(orderDto);
        return ApiResponse.ok(uuid);
    }
}
