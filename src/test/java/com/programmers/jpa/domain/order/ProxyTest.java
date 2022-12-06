package com.programmers.jpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.programmers.jpa.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
public class ProxyTest {
    @Autowired
    private EntityManagerFactory emf;

    Object uuid;

    @BeforeEach
    void setUp() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        uuid = order.getUuid().toString();

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("kanghonggu");
        member.setNickName("guppy.kang");
        member.setAge(33);
        member.setAddress("서울시 동작구만 움직이면쏜다.");
        member.setDescription("KDT 화이팅");

        member.addOrder(order); // 연관관계 편의 메소드 사용
        entityManager.persist(member);
        transaction.commit();
    }

    @Test
    void proxy() {
        EntityManager entityManager = emf.createEntityManager();
        Order order = entityManager.find(Order.class, uuid);

        Member member = order.getMember();
        log.info("MEMBER USE BEFORE IS LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member));
        String nickName = member.getNickName();
        log.info("MEMBER USE AFTER IS LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member));
    }

    @Test
    void move_persist() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Order order = entityManager.find(Order.class, uuid); //영속상태
        transaction.begin();

        OrderItem orderItem = new OrderItem(); //준영속상태 cascade.All로 영속상태 전이
        orderItem.setQuantity(10);
        orderItem.setPrice(1000);

        order.addOrderItem(orderItem);

        transaction.commit(); //flush 할 때 orderItem은 영속성 전이가 일어나지 않아서 오류가난다.

        entityManager.clear();
        Order order2 = entityManager.find(Order.class, uuid); //영속상태


        transaction.begin();

        order2.getOrderItems().remove(0); //고아상태 delete 쿼리 날라가지 않음 -> orphanRemoval = true 로 고아되면 flush시 삭제

        transaction.commit(); //Flush;
    }
}
