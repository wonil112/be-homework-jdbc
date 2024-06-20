package com.springboot.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderPageInfo {

    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
}
