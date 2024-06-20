package com.springboot.order.repository;

import com.springboot.order.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
