package com.programmers.jpa.repository;

import com.programmers.jpa.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    void save(Customer customer);
    Customer findById(long id);
}
