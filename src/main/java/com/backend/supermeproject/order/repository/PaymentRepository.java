package com.backend.supermeproject.order.repository;

import com.backend.supermeproject.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // 사용자 ID와 결제 ID로 결제 내역 조회
    Optional<Payment> findByIdAndMember_MemberId(Long paymentId, Long memberId);


}
