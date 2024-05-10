package com.backend.supermeproject.order.service;


import com.backend.supermeproject.cart.entity.CartItem;
import com.backend.supermeproject.cart.repository.CartItemRepository;
import com.backend.supermeproject.global.exception.BusinessException;
import com.backend.supermeproject.global.exception.ErrorCode;
import com.backend.supermeproject.item.entity.Item;
import com.backend.supermeproject.item.entity.Size;
import com.backend.supermeproject.item.repository.ItemRepository;
import com.backend.supermeproject.order.dto.OrderDto;
import com.backend.supermeproject.order.entity.Order;
import com.backend.supermeproject.order.entity.OrderItem;
import com.backend.supermeproject.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;


    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.cartItemRepository = cartItemRepository;
    }



    /**
     * 주문 생성 로직
     * 결제되지 않은 카트 아이템 기반으로 주문을 생성하고, 각 아이템의 재고를 확인 및 감소시킵니다.
     * @param orderDto 주문 데이터 전송 객체
     * @param memberId 주문을 생성하는 사용자의 ID
     */
    @Transactional
    public void createOrder(OrderDto orderDto, Long memberId) {
        log.info("사용자 ID {}로 주문 생성을 시작합니다.", memberId);
        List<CartItem> cartItems = cartItemRepository.findByCart_Member_MemberIdAndCart_IsPaidFalse(memberId);
        if (cartItems.isEmpty()) {
            throw new BusinessException(ErrorCode.CART_EMPTY);
        }

        Order order = new Order();
        order.setMemberId(memberId);
        order.setStatus("PENDING");
        order.setShippingCountry(orderDto.getShippingCountry());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setShippingCity(orderDto.getShippingCity());
        order.setShippingPostcode(orderDto.getShippingPostcode());
        order.setShippingPhoneNumber(orderDto.getShippingPhoneNumber());
        BigDecimal calculatedTotalPrice = BigDecimal.ZERO;


        for (CartItem cartItem : cartItems) {
            Size size = cartItem.getSize();
            Item item = size.getVariant().getItem();
            if (size.getStock() < cartItem.getQuantity()) {
                throw new BusinessException(ErrorCode.OUT_OF_STOCK, "상품 이름: " + item.getProductName());
            }

            BigDecimal itemTotalPrice = item.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())); // 가격 계산
            OrderItem orderItem = OrderItem.createOrderItem(item, cartItem.getQuantity());
            order.addItem(orderItem);

            calculatedTotalPrice = calculatedTotalPrice.add(itemTotalPrice);  // 총 가격 계산
        }

        BigDecimal orderTotalPrice = orderDto.getTotalPrice(); // 이렇게 직접 할당하면 됩니다.
        if (calculatedTotalPrice.compareTo(orderTotalPrice) != 0) {
            throw new BusinessException(ErrorCode.PRICE_MISMATCH);
        }



        order.setTotalPrice(calculatedTotalPrice);
        orderRepository.save(order);
        log.info("사용자 ID {}에 대한 주문이 성공적으로 생성되었습니다.", memberId);
        cartItemRepository.deleteAll(cartItems);
    }

    /**
     * 특정 주문의 상세 정보를 조회
     * @param orderId 조회하고자 하는 주문의 ID
     * @param memberId 조회를 요청한 사용자의 ID
     * @return 조회된 주문의 상세 정보를 담은 DTO
     */
    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long orderId, Long memberId) {
        Order order = orderRepository.findByIdAndMemberId(orderId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        log.info("주문 ID {} 조회 완료", orderId);
        return OrderDto.fromEntity(order);
    }


    /**
     * 사용자 ID에 따라 모든 주문을 조회
     * @param memberId 조회를 요청한 사용자의 ID
     * @return 사용자의 모든 주문 목록
     */
    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrdersByMemberId(Long memberId) {
        List<Order> orders = orderRepository.findAllByMemberId(memberId);
        log.info("사용자 ID {}의 모든 주문을 조회했습니다.", memberId);
        return orders.stream().map(OrderDto::fromEntity).collect(Collectors.toList());
    }

}
