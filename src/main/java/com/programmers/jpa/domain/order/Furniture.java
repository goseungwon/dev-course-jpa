package com.programmers.jpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Furniture extends Item {
    private int width;
    private int height;
}
