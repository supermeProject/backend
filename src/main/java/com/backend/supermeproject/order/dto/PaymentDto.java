package com.backend.supermeproject.order.dto;

import com.backend.supermeproject.order.entity.Payment;

import java.math.BigDecimal;

public record PaymentDto(
        Long paymentId,
        Long orderId,
        Long memberId,
        BigDecimal amount,
        String method,
        String responseCode,
        String status,
        String shippingCountry,
        String shippingAddress,
        String shippingCity,
        String shippingPostcode,
        String shippingPhoneNumber
) {
    public static PaymentDto fromPayment(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getOrderId(),
                payment.getMember().getMemberId(),
                payment.getAmount(),
                payment.getPaymentMethod().name(),
                null, // Assuming 'responseCode' needs to be set from somewhere else or could be null
                payment.getStatus().name(),
                payment.getShippingCountry(),
                payment.getShippingAddress(),
                payment.getShippingCity(),
                payment.getShippingPostcode(),
                payment.getShippingPhoneNumber()
        );
    }
}
