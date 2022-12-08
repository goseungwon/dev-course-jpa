package com.programmers.jpa.order.service;

import com.programmers.jpa.domain.order.OrderRepository;
import com.programmers.jpa.domain.order.OrderStatus;
import com.programmers.jpa.order.dto.*;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void save_test() {
        //given
        OrderDto orderDto = OrderDto.builder()
                .uuid(uuid)
                .memo("문 앞에 보관 해주세요")
                .orderDateTime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("koseungwon")
                                .nickName("winner")
                                .address("hanam")
                                .age(25)
                                .description("desc")
                                .build()
                )
                .orderItemDtos(List.of(
                        OrderItemDto.builder()
                                .price(1000)
                                .quantity(100)
                                .itemDtos(List.of(
                                        ItemDto.builder()
                                                .type(ItemType.FOOD)
                                                .chef("Baek")
                                                .price(1000)
                                                .build()
                                ))
                                .build()
                ))
                .build();

        //when
        String savedUuid = orderService.save(orderDto);

        //then
        assertEquals(savedUuid, uuid);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void findOneTest() throws NotFoundException {
        //given
        String orderUuid = uuid;

        //when
        OrderDto one = orderService.findOne(uuid);

        //then
        assertEquals(one.getUuid(), orderUuid);
    }

    @Test
    void findAllTest() {
        //given
        PageRequest page = PageRequest.of(0, 10);

        //when
        Page<OrderDto> all = orderService.findAll(page);

        //then
        assertEquals(all.getTotalElements(), 1);
    }

}