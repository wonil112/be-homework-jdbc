package com.springboot.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@AllArgsConstructor
@Table("ORDER_COFFEE")
public class OrderCoffee {
    private long coffeeId;
    private int quantity;
}
