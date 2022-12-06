package com.programmers.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void 저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("seungwon");
        customer.setLastName("go");
        customer.setAge(25);

        entityManager.persist(customer);

        transaction.commit();

        entityManager.detach(customer);

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void 수정() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("seungwon");
        customer.setLastName("go");
        customer.setAge(25);

        entityManager.persist(customer);

        transaction.commit();

        transaction.begin();
        customer.setFirstName("winner");
        customer.setLastName("ko");

        transaction.commit();

        log.info("{} {}", customer.getFirstName(), customer.getLastName());
    }

    @Test
    void 삭제() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("seungwon");
        customer.setLastName("go");
        customer.setAge(25);

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        entityManager.remove(customer);

        transaction.commit();
    }

    @Test
    void 조회_db조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("seungwon");
        customer.setLastName("go");
        customer.setAge(25);

        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getLastName(), selected.getFirstName());
    }

    @Test
    void 조회_1차캐시() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("seungwon");
        customer.setLastName("go");
        customer.setAge(25);

        entityManager.persist(customer);

        transaction.commit();

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getLastName(), selected.getFirstName());
    }
}
