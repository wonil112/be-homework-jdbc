package com.springboot.order.service;

import com.springboot.coffee.service.CoffeeService;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.service.MemberService;
import com.springboot.order.entity.Order;
import com.springboot.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    final private OrderRepository orderRepository;
    final private MemberService memberService;
    final private CoffeeService coffeeService;

    public OrderService(OrderRepository orderRepository,
                        MemberService memberService,
                        CoffeeService coffeeService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.coffeeService = coffeeService;
    }

    public Order createOrder(Order order) {
        // 회원이 존재하는지 확인
        memberService.findVerifiedMember(order.getMemberId());

        // 커피가 존재하는지 조회해야 됨
        order.getOrderCoffees()
                .stream()
                .forEach(orderCoffee -> {
                    coffeeService.findVerifiedCoffee(orderCoffee.getCoffeeId());
                });
        return orderRepository.save(order);
    }

    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

    public Page<Order> findOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").descending());
        return orderRepository.findAll(pageable);
    }

    public void cancelOrder(long orderId) {
        Order findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();

        // OrderStatus의 step이 2 미만일 경우(ORDER_CONFIRM)에만 주문 취소가 되도록한다.
        if (step >= 2) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        orderRepository.save(findOrder);
    }

    private Order findVerifiedOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder =
                optionalOrder.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }
}
