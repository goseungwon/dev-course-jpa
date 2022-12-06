package com.programmers.jpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {
    @Id
    @Column(name = "id")
    private String uuid;
    private String memo;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    @Column(insertable = false, updatable = false)
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }
}
