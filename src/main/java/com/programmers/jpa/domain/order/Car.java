package com.programmers.jpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Car extends Item {
    private int power;
}
