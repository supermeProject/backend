package com.backend.supermeproject.cart.service;

import com.backend.supermeproject.cart.dto.CartItemDto;
import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.entity.CartItem;
import com.backend.supermeproject.cart.entity.QCart;
import com.backend.supermeproject.cart.repository.CartItemRepository;
import com.backend.supermeproject.cart.repository.CartRepository;
import com.backend.supermeproject.global.exception.BusinessException;
import com.backend.supermeproject.global.exception.ErrorCode;
import com.backend.supermeproject.item.entity.Item;
import com.backend.supermeproject.item.repository.ItemRepository;
import com.backend.supermeproject.member.entity.Member;
import com.backend.supermeproject.member.repository.MemberRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, ItemRepository itemRepository, MemberRepository memberRepository, CartRepository cartRepository, PlatformTransactionManager transactionManager) {
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
        this.memberRepository = memberRepository;
        this.cartRepository = cartRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    // 새 카트 아이템 추가
    public void addCartItem(Long memberId, CartItemDto cartItemDto) {
        log.info("사용자 ID {}에 아이템 ID {}을 추가 시도합니다.", memberId, cartItemDto.getItemId());
        // 아이템 유효성 확인
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

        // 사용자의 카트 조회, 없으면 새로 생성
        Cart cart = cartRepository.findByMember_MemberId(memberId)
                .orElseGet(() -> {
                    log.info("사용자 ID {}의 카트가 없어 새로 생성합니다.", memberId);
                    return cartRepository.save(new Cart(memberRepository.findById(memberId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER))));
                });
        // 카트 아이템 생성 및 저장
        CartItem newCartItem = CartItem.builder()
                .cart(cart)
                .item(item)
                .quantity(cartItemDto.getQuantity())
                .build();
        log.info("사용자 ID {}의 카트에 아이템을 추가합니다.", memberId);
        cartItemRepository.save(newCartItem);

    }

    // 사용자 ID로 장바구니 조회
    public List<CartItemDto> getCartItems(Long memberId) {
        log.info("사용자 ID {}에서 모든 아이템을 조회합니다.", memberId);
        List<CartItem> cartItems = cartItemRepository.findByCart_Member_MemberId(memberId);
        List<CartItemDto> items = cartItems.stream()
                .map(cartItem -> {
                    String imageUrl = cartItem.getItem().getImage().isEmpty() ? null : cartItem.getItem().getImage().get(0).getFilePath();
                    return CartItemDto.builder()
                            .itemId(cartItem.getItem().getItemId())
                            .productName(cartItem.getItem().getProductName())
                            .quantity(cartItem.getQuantity())
                            .price(cartItem.getItem().getPrice())
                            .imageURL(imageUrl)
                            .build();
                })
                .collect(Collectors.toList());

        log.info("사용자 ID {}에서 조회된 아이템 수: {}", memberId, items.size());
        if (items.isEmpty()) {
            throw new BusinessException(ErrorCode.CART_NO_PRODUCTS);
        }
        return items;
    }


    // 카트 아이템 수량 업데이트
    public void updateCartItemQuantity(Long memberId, Long itemId, int quantity) {
        log.info("사용자 ID {}의 아이템 ID {} 수량을 업데이트합니다.", memberId, itemId);
        CartItem cartItem = cartItemRepository.findByIdAndCart_Member_MemberId(itemId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));

        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.INVALID_CART_ITEM_QUANTITY);
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        log.info("사용자 ID {}의 아이템 ID {} 수량 업데이트 완료", memberId, itemId);
    }

    // 카트 아이템 삭제
    @Transactional
    public void removeCartItem(Long memberId, Long itemId) {
        log.info("사용자 ID {}에서 아이템 ID {}를 삭제합니다.", memberId, itemId);
        cartItemRepository.findByIdAndCart_Member_MemberId(itemId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.deleteCartItemById(itemId);
        log.info("아이템 ID {} 삭제 완료", itemId);
    }

    // 카트 내 모든 아이템 삭제
    public void clearCart(Long memberId) {
        log.info("사용자 ID {}의 모든 아이템을 삭제합니다.", memberId);
        Optional.ofNullable(cartItemRepository.findByCart_Member_MemberId(memberId))
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CART));

        cartItemRepository.clearCartByMemberId(memberId);
        log.info("사용자 ID {}의 모든 아이템 삭제 완료", memberId);
    }

//    // 결제 여부 확인 및 미결제 카트 조회
//    public Cart findUnpaidCartByMemberId(Long memberId) {
//        QCart qCart = QCart.cart;
//        BooleanExpression isMember = qCart.member.memberId.eq(memberId);
//        BooleanExpression isUnpaid = qCart.isPaid.isFalse();
//
//        return cartRepository.findOne(isMember.and(isUnpaid)).orElse(null);
//    }

}
