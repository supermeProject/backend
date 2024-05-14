package com.backend.supermeproject.order.controller;


import com.backend.supermeproject.member.jwt.MemberInfo;
import com.backend.supermeproject.order.dto.PaymentDto;
import com.backend.supermeproject.order.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name="결제 api", description = "결제 실행과 결제 상세 정보 조회 기능 api")
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }


    // 결제 실행
    @Operation(summary = "결제 처리 API")
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(
            @RequestBody PaymentDto paymentDto,
            @AuthenticationPrincipal MemberInfo member) {
         Long memberId = member.getMember().getMemberId();
        paymentService.processPayment(paymentDto, memberId);
        return ResponseEntity.ok("결제가 성공적으로 처리되었습니다.");
    }


    // 결제 상세 조회
    @Operation(summary = "결제 상세 조회 API")
    @GetMapping("/details/{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentById(
            @PathVariable Long paymentId,
            @AuthenticationPrincipal MemberInfo member) {
         Long memberId = member.getMember().getMemberId();
        PaymentDto paymentDto = paymentService.getPaymentById(paymentId, memberId);
        return ResponseEntity.ok(paymentDto);
    }


}
