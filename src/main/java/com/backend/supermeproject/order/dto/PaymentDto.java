package com.backend.supermeproject.order.dto;

import com.backend.supermeproject.order.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private Long orderId;
    private Long memberId;
    private BigDecimal amount;
    private String method;
    private String status;

    public PaymentDto(Payment payment) {
        this.paymentId = payment.getId();
        this.orderId = payment.getOrderId();
        this.memberId = payment.getMember().getMemberId(); // Member 객체에서 ID 추출
        this.amount = payment.getAmount();
        this.method = payment.getPaymentMethod().name(); // Enum 값을 String으로 변환
        this.status = payment.getStatus().name(); // Enum 값을 String으로 변환
    }


}
