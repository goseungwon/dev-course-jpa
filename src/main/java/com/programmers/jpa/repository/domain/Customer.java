package com.programmers.jpa.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("customers")
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    private long id;
    private String firstName;
    private String lastName;
}
