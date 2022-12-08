package com.programmers.jpa.order.service;

import com.programmers.jpa.domain.order.Order;
import com.programmers.jpa.domain.order.OrderRepository;
import com.programmers.jpa.order.converter.OrderConverter;
import com.programmers.jpa.order.dto.OrderDto;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Transactional
    public String save(OrderDto dto) {
        //transaction.begin -> commit 은 Transactional 어노테이션으로 사용
        //dto -> entity 로 변환 (준영속)
        Order order = orderConverter.convertOrder(dto);
        //repository.save 로 영속화
        Order entity = orderRepository.save(order);
        //결과 반환
        return entity.getUuid();
    }

    @Transactional
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderConverter::convertOrderDto);
    }

    @Transactional
    public OrderDto findOne(String uuid) throws NotFoundException {
        //조회를 위한 키값 인자로 받기
        // findById 하기 (영속화된 엔티티)
        return orderRepository.findById(uuid)
                .map(orderConverter::convertOrderDto)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
        // entity -> dto 트랜잭션 관리 밖으로 보내기 위해 (의도치 않는 곳에서 쿼리가 나감)
    }
}
