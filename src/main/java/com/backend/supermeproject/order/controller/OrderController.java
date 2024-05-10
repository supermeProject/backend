package com.backend.supermeproject.order.controller;

import com.backend.supermeproject.cart.service.CartService;
import com.backend.supermeproject.member.jwt.MemberInfo;
import com.backend.supermeproject.order.dto.OrderDto;
import com.backend.supermeproject.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name="주문 api", description = "주문 생성, 주문 상세 조회, 주문 목록 조회 기능 api")
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    /**
     * 현재 인증된 사용자의 ID를 가져오는 메서드입니다.
     * SecurityContextHolder에서 Authentication 객체를 추출하여 memberId를 반환합니다.
     * @return 현재 인증된 사용자의 memberId
     */
    private Long getMemberIdFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((MemberInfo) authentication.getPrincipal()).getMember().getMemberId();
    }

    // 주문 생성
    @Operation(summary = "주문 생성 API")
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        Long memberId = getMemberIdFromAuth();
        orderService.createOrder(orderDto, memberId);
        return ResponseEntity.ok("주문이 성공적으로 생성되었습니다.");
    }

    // 주문 상세 조회
    @Operation(summary = "특정 주문 조회 API")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        Long memberId = getMemberIdFromAuth();
        OrderDto orderDto = orderService.getOrderById(orderId, memberId);
        return ResponseEntity.ok(orderDto);
    }


    // 모든 주문 조회
    @Operation(summary = "모든 주문 조회 API")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        Long memberId = getMemberIdFromAuth();
        return ResponseEntity.ok(orderService.getAllOrdersByMemberId(memberId));
    }




}
