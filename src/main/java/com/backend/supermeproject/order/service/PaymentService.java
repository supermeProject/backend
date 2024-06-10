package com.backend.supermeproject.order.service;

import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.repository.CartRepository;
import com.backend.supermeproject.global.exception.BusinessException;
import com.backend.supermeproject.global.exception.ErrorCode;
import com.backend.supermeproject.member.entity.Member;
import com.backend.supermeproject.member.repository.MemberRepository;
import com.backend.supermeproject.order.dto.PaymentDto;
import com.backend.supermeproject.order.entity.Order;
import com.backend.supermeproject.order.entity.Payment;
import com.backend.supermeproject.order.entity.PaymentMethod;
import com.backend.supermeproject.order.entity.PaymentStatus;
import com.backend.supermeproject.order.repository.OrderRepository;
import com.backend.supermeproject.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service @RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;


    // 결제 처리
    @Transactional
    public PaymentDto processPayment(PaymentDto paymentDto, Long memberId) {
        // 회원 정보 및 주문 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        Order order = orderRepository.findById(paymentDto.orderId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        log.info("결제 처리를 시작합니다: 회원 ID = {}, 주문 ID = {},결제 방법: {}", memberId, paymentDto.orderId(),paymentDto.method());
        PaymentMethod method = PaymentMethod.valueOf(paymentDto.method().toUpperCase());

        //결제 방법에 따른 결제
        switch (method) {
            case PAYPAL:
                processPaypalPayment(paymentDto, member);
                break;
            case DEPOSIT:
                processDepositPayment(paymentDto, member);
                break;
            default:
                throw new BusinessException(ErrorCode.INVALID_PAYMENT_METHOD);
        }

        // 결제 정보 생성 및 저장
        Payment payment = Payment.builder()
                .member(member)
                .orderId(paymentDto.orderId())
                .amount(paymentDto.amount())
                .paymentMethod(PaymentMethod.valueOf(paymentDto.method()))
                .status(PaymentStatus.COMPLETED)
                .build();
        paymentRepository.save(payment);

        // 카트 상태 업데이트
        updateCartStatus(memberId);

        log.info("결제가 완료되었습니다. 결제 ID: {}", payment.getId());
        return buildPaymentDto(payment, member, paymentDto);
    }

    // 결제 상세 조회
    public PaymentDto getPaymentById(Long paymentId, Long memberId) {
        log.info("결제 상세 조회 시작: 결제 ID {}", paymentId);
        Payment payment = paymentRepository.findByIdAndMember_MemberId(paymentId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));

        log.info("결제 상세 조회 완료: 결제 ID {}", paymentId);
        return buildPaymentDto(payment, payment.getMember(), null);
            }

    // PaymentDto 빌드를 위한 공통 메서드
    private PaymentDto buildPaymentDto(Payment payment, Member member, PaymentDto requestDetails) {
        return new PaymentDto(
                payment.getId(),
                payment.getOrderId(),
                member.getMemberId(),
                payment.getAmount(),
                payment.getPaymentMethod().name(),
                payment.getStatus().name(),
                requestDetails != null ? requestDetails.shippingCountry() : null,
                requestDetails != null ? requestDetails.shippingAddress() : null,
                requestDetails != null ? requestDetails.shippingCity() : null,
                requestDetails != null ? requestDetails.shippingPostcode() : null,
                requestDetails != null ? requestDetails.shippingPhoneNumber() : null,
                requestDetails != null ? requestDetails.responseCode() : null
        );
    }


    // 카트 상태 업데이트
    private void updateCartStatus(Long memberId) {
        List<Cart> carts = cartRepository.findByMember_MemberIdAndIsPaidFalse(memberId);
        carts.forEach(cart -> {
            cart.setPaid(true);
            cartRepository.save(cart);
        });
        log.info("카트 상태 업데이트 완료: 회원 ID {}", memberId);
    }

    //페이팔 결제
    private void processPaypalPayment(PaymentDto paymentDto, Member member) {
        log.info("페이팔 결제를 처리합니다. 응답 코드를 확인하세요.");
        // 실제 페이팔 결제 처리 로직 (임의의 코드)
        if (!"PAYPAL:SUCCESS".equals(paymentDto.responseCode())) {
            throw new BusinessException(ErrorCode.PAYMENT_FAILED);
        }
    }

    //디파짓 결제 확인
    private void processDepositPayment(PaymentDto paymentDto, Member member) {
        // 사용자 잔액 확인 및 차감
        BigDecimal paymentAmount = paymentDto.amount();
        if (member.getDeposit().compareTo(paymentAmount) < 0) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_FUNDS);
        }
        BigDecimal newDeposit = member.getDeposit().subtract(paymentAmount);
        member.setDeposit(newDeposit); // 업데이트된 잔액 설정
        memberRepository.save(member);

            log.info("디파짓으로 결제 처리됨: 사용자 잔액 업데이트");
    }

}
