package com.springboot.order.mapper;

import com.springboot.coffee.entity.Coffee;
import com.springboot.order.entity.OrderCoffee;
import com.springboot.coffee.service.CoffeeService;
import com.springboot.order.dto.OrderCoffeeResponseDto;
import com.springboot.order.dto.OrderPostDto;
import com.springboot.order.dto.OrderResponseDto;
import com.springboot.order.entity.Order;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // TODO 수정되었음.
    default Order orderPostDtoToOrder(OrderPostDto orderPostDto) {
        Order order = new Order();
        order.setMemberId(orderPostDto.getMemberId());
        Set<OrderCoffee> orderCoffees = orderPostDto.getOrderCoffees()
                .stream()
                .map(orderCoffeeDto -> new OrderCoffee(orderCoffeeDto.getCoffeeId(),
                        orderCoffeeDto.getQuantity()))
                .collect(Collectors.toSet());
        order.setOrderCoffees(orderCoffees);
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    default OrderResponseDto orderToOrderResponseDto(CoffeeService coffeeService,
                                                     Order order) {

        long memberId = order.getMemberId();

        List<OrderCoffeeResponseDto> orderCoffees =
                orderToOrderCoffeeResponseDto(coffeeService, order.getOrderCoffees());

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderCoffees(orderCoffees);
        orderResponseDto.setMemberId(memberId);
        orderResponseDto.setCreatedAt(order.getCreatedAt());
        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setOrderStatus(order.getOrderStatus());

        // TODO 주문에 대한 더 자세한 정보로의 변환은 요구 사항에 따라 다를 수 있습니다.

        return orderResponseDto;
    }

    default List<OrderCoffeeResponseDto> orderToOrderCoffeeResponseDto(
            CoffeeService coffeeService,
            Set<OrderCoffee> orderCoffees) {
        return orderCoffees.stream()
                .map(orderCoffee -> {
                    Coffee coffee = coffeeService.findCoffee(orderCoffee.getCoffeeId());
                    return new com.springboot.order.dto.OrderCoffeeResponseDto(coffee.getCoffeeId(),
                            coffee.getKorName(),
                            coffee.getEngName(),
                            coffee.getPrice(),
                            orderCoffee.getQuantity());
                }).collect(Collectors.toList());
    }
}
