package com.programmers.jpa.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpa.domain.order.OrderStatus;
import com.programmers.jpa.order.dto.*;
import com.programmers.jpa.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    String uuid = UUID.randomUUID().toString();

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void saveCallTest() throws Exception {
        //given
        OrderDto orderDto = OrderDto.builder()
                .uuid(UUID.randomUUID().toString())
                .memo("문 앞에 보관 해주세요")
                .orderDateTime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("chldydgns")
                                .nickName("porche")
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

        //when  //then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-save",
                        requestFields(
                                fieldWithPath("uuid").type(JsonFieldType.STRING).description("UUID"),
                                fieldWithPath("memo").type(JsonFieldType.STRING).description("memo"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("orderStatus"),
                                fieldWithPath("orderDateTime").type(JsonFieldType.STRING).description("orderDateTime"),
                                fieldWithPath("memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                                fieldWithPath("memberDto.id").type(JsonFieldType.NULL).description("memberDto.id"),
                                fieldWithPath("memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                                fieldWithPath("memberDto.nickName").type(JsonFieldType.STRING).description("memberDto.nickName"),
                                fieldWithPath("memberDto.age").type(JsonFieldType.NUMBER).description("memberDto.age"),
                                fieldWithPath("memberDto.address").type(JsonFieldType.STRING).description("memberDto.address"),
                                fieldWithPath("memberDto.description").type(JsonFieldType.STRING).description("memberDto.description"),
                                fieldWithPath("orderItemDtos[]").type(JsonFieldType.ARRAY).description("orderItemDtos[]"),
                                fieldWithPath("orderItemDtos[].id").type(JsonFieldType.NULL).description("orderItemDtos[].id"),
                                fieldWithPath("orderItemDtos[].price").type(JsonFieldType.NUMBER).description("orderItemDtos[].price"),
                                fieldWithPath("orderItemDtos[].quantity").type(JsonFieldType.NUMBER).description("orderItemDtos[].quantity"),
                                fieldWithPath("orderItemDtos[].itemDtos[]").type(JsonFieldType.ARRAY).description("orderItemDtos[].itemDtos[]"),
                                fieldWithPath("orderItemDtos[].itemDtos[].price").type(JsonFieldType.NUMBER).description("orderItemDtos[].itemDtos[].price"),
                                fieldWithPath("orderItemDtos[].itemDtos[].stockQuantity").type(JsonFieldType.NUMBER).description("orderItemDtos[].itemDtos[].stockQuantity"),
                                fieldWithPath("orderItemDtos[].itemDtos[].type").type(JsonFieldType.STRING).description("orderItemDtos[].itemDtos[].type"),
                                fieldWithPath("orderItemDtos[].itemDtos[].chef").type(JsonFieldType.STRING).description("orderItemDtos[].itemDtos[].chef")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/orders/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/orders")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}