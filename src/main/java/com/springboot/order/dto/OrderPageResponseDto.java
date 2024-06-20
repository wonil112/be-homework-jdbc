package com.springboot.order.dto;

import com.springboot.response.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderPageResponseDto {

    private List<OrderResponseDto> orderResponseDtos;
    private PageInfo pageInfo;
}
