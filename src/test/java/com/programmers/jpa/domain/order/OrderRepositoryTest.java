package com.programmers.jpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void test() {
        Order order = new Order();
        String uuid = UUID.randomUUID().toString();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setCreatedBy("goSW");
        order.setCreatedAt(LocalDateTime.now());
        order.setMemo("memomemo");

        orderRepository.save(order);

        Order order1 = orderRepository.findById(uuid).get();
        List<Order> all = orderRepository.findAll();

        orderRepository.findAllByOrderStatus(OrderStatus.OPENED);
        orderRepository.findAllByOrderStatusOrderByOrderDateTime(OrderStatus.OPENED);

        orderRepository.findByMemo("S");
    }

}