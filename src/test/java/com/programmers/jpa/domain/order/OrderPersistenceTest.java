package com.programmers.jpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert() {
        Member member = new Member();
        member.setName("seungwon");
        member.setDescription("데브코스 멘티");
        member.setAge(25);
        member.setAddress("경기도 하남시");
        member.setNickName("winner");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(member);

        transaction.commit();
    }

    @Test
    void 잘못된_설계() {
        Member member = new Member();
        member.setName("seungwon");
        member.setDescription("데브코스 멘티");
        member.setAge(25);
        member.setAddress("경기도 하남시");
        member.setNickName("winner");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(member);
        Member memberEntity = em.find(Member.class, 1L);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("부재시 연락주세요");
        order.setMemberId(memberEntity.getId());

        em.persist(order);
        transaction.commit();

        Order orderEntity = em.find(Order.class, order.getUuid());
        Member orderMemberEntity = em.find(Member.class, orderEntity.getMemberId());
        //객체 중심이라면 객체그래프 설계를 할 수 있어야지 않을까?

        log.info("nick : {}", orderMemberEntity.getNickName());
    }

    @Test
    void 연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setName("seungwon");
        member.setNickName("winner");
        member.setAge(25);
        member.setAddress("hanam");

        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("asdf");
        order.setMember(member);
//        member.setOrders(Lists.newArrayList(order)); //매핑 완료

        em.persist(order);

        transaction.commit();

        em.clear();
        Order entity = em.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getNickName()); //객체 그래프 탐색
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size()); //연관관계 매핑 잘안됨
    }
}
