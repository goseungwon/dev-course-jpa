//package com.programmers.jpa;
//
//import com.programmers.jpa.domain.CustomerRepository;
//import com.programmers.jpa.repository.domain.CustomerEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@SpringBootTest
//@Slf4j
//public class JPATest {
//
//    @Autowired
//    CustomerRepository customerRepository;
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @AfterEach
//    void tearDown() {
//        customerRepository.deleteAll();;
//    }
//
//    @Test
//    void INSERT_TEST() {
//        //given
//        CustomerEntity customer = new CustomerEntity();
//        customer.setId(1L);
//        customer.setFirstName("honggu");
//        customer.setLastName("kang");
//        customer.setAge(33);
//
//        //when
//        customerRepository.save(customer);
//
//        //then
//        CustomerEntity entity = customerRepository.findById(1L).get();
//        log.info("{} {}", entity.getFirstName(), entity.getLastName());
//
//    }
//
//    @Test
//    @Transactional
//    void UPDATE_TEST() {
//        //given
//        CustomerEntity customer = new CustomerEntity();
//        customer.setId(1L);
//        customer.setFirstName("honggu");
//        customer.setLastName("kang");
//        customer.setAge(33);
//        customerRepository.save(customer);
//
//        //when
//        CustomerEntity entity = customerRepository.findById(1L).get();
//        entity.setFirstName("guppy");
//        entity.setLastName("hong");
//        entity.setAge(25);
//
//        CustomerEntity updated = customerRepository.findById(1L).get();
//        log.info("{} {}", updated.getFirstName(), updated.getLastName());
//        log.info("age : {}", entity.getAge());
//    }
//
//}
