package com.backend.supermeproject.order.service;

import com.backend.supermeproject.global.exception.BusinessException;
import com.backend.supermeproject.global.exception.ErrorCode;
import com.backend.supermeproject.item.repository.ItemRepository;
import com.backend.supermeproject.member.entity.Member;
import com.backend.supermeproject.member.repository.MemberRepository;
import com.backend.supermeproject.order.dto.PaymentDto;
import com.backend.supermeproject.order.entity.Payment;
import com.backend.supermeproject.order.entity.PaymentMethod;
import com.backend.supermeproject.order.entity.PaymentStatus;
import com.backend.supermeproject.order.repository.OrderRepository;
import com.backend.supermeproject.order.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, MemberRepository memberRepository, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    // 결제 처리
    @Transactional
    public PaymentDto processPayment(PaymentDto paymentDto, Long memberId) {
        // 회원 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));


        log.info("결제 처리 시작: 회원 ID {}", memberId);
        Payment payment = Payment.builder()
                .member(member)
                .orderId(paymentDto.getOrderId())
                .amount(paymentDto.getAmount())
                .paymentMethod(PaymentMethod.valueOf(paymentDto.getMethod()))
                .status(PaymentStatus.COMPLETED)
                .build();
        paymentRepository.save(payment);
        log.info("결제 처리 완료: 주문 ID {}", paymentDto.getOrderId());

        // 로그: 결제 처리 로직 완료
        return new PaymentDto(
                payment.getId(),
                payment.getOrderId(),
                member.getMemberId(),
                payment.getAmount(),
                payment.getPaymentMethod().name(),
                payment.getStatus().name());
    }

    // 결제 상세 조회
    public PaymentDto getPaymentById(Long paymentId, Long memberId) {
        log.info("결제 상세 조회 시작: 결제 ID {}", paymentId);
        Payment payment = paymentRepository.findByIdAndMember_MemberId(paymentId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));

        log.info("결제 상세 조회 완료: 결제 ID {}", paymentId);
        return new PaymentDto(
                payment.getId(),
                payment.getOrderId(),
                payment.getMember().getMemberId(),
                payment.getAmount(),
                payment.getPaymentMethod().name(),
                payment.getStatus().name());
    }


    private void processPaypalPayment(PaymentDto paymentDto, Member member) {
        // 페이팔 결제 로직 구현
        // 예: API 호출, 결제 확인 등
    }

    //디파짓 결제 확인
    private void processDepositPayment(PaymentDto paymentDto, Member member) {
        // 사용자 잔액 확인 및 차감
        BigDecimal paymentAmount = paymentDto.getAmount();
        if (member.getDeposit().compareTo(paymentAmount) < 0) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_FUNDS);
        }
        BigDecimal newDeposit = member.getDeposit().subtract(paymentAmount);
        member.setDeposit(newDeposit); // 업데이트된 잔액 설정
        memberRepository.save(member);
    }

}
