package com.programmers.jpa.domain;

import com.programmers.jpa.domain.Customer;
import com.programmers.jpa.domain.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

//    @BeforeAll
//    void before() {
////        repository.drop
//    }

    @Autowired
    private CustomerRepository repository;

    @Test
    void test() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("seungwon");
        customer.setLastName("go");
        customer.setAge(25);

        //when
        repository.save(customer);

        //then
        Customer entity = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

}