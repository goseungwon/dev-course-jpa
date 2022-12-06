package com.programmers.jpa.domain.order;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
public class Parent {
    @EmbeddedId
    private ParentId id;
}
