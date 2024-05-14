package com.backend.supermeproject.order.controller;

import com.backend.supermeproject.member.jwt.MemberInfo;
import com.backend.supermeproject.order.dto.OrderDto;
import com.backend.supermeproject.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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

    // 주문 생성
    @Operation(summary = "주문 생성 API")
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(
            @RequestBody OrderDto orderDto,
            @AuthenticationPrincipal MemberInfo member) {
         Long memberId = member.getMember().getMemberId();
        orderService.createOrder(orderDto, memberId,false);
        return ResponseEntity.ok("주문이 성공적으로 생성되었습니다.");
    }

    // 주문 상세 조회
    @Operation(summary = "특정 주문 조회 API")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal MemberInfo member) {
         Long memberId = member.getMember().getMemberId();
        OrderDto orderDto = orderService.getOrderById(orderId, memberId);
        return ResponseEntity.ok(orderDto);
    }


    // 모든 주문 조회
    @Operation(summary = "모든 주문 조회 API")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(
            @AuthenticationPrincipal MemberInfo member) {
         Long memberId = member.getMember().getMemberId();
        return ResponseEntity.ok(orderService.getAllOrdersByMemberId(memberId));
    }




}
