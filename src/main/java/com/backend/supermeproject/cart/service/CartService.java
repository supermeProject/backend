package com.backend.supermeproject.cart.service;

import com.backend.supermeproject.cart.dto.CartItemDto;
import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.entity.CartItem;
import com.backend.supermeproject.cart.entity.QCart;
import com.backend.supermeproject.cart.repository.CartItemRepository;
import com.backend.supermeproject.cart.repository.CartRepository;
import com.backend.supermeproject.global.exception.BusinessException;
import com.backend.supermeproject.global.exception.ErrorCode;
import com.backend.supermeproject.image.ImageEntity.ItemImage;
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


        // 기존 활성 카트를 검색하고, 결제 완료된 경우 새 카트 생성
        Cart cart = cartRepository.findLatestActiveCartByMemberId(memberId)
                .orElseGet(() -> createNewCart(memberRepository.findById(memberId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER))));

        // CartItem 객체 생성 변경
        CartItem newCartItem = CartItem.builder()
                .cart(cart)
                .item(item)
                .quantity(cartItemDto.getQuantity())
                .build();
        cartItemRepository.save(newCartItem);
        log.info("멤버 ID {}의 카트에 아이템 추가 완료", memberId);
    }

    private Cart createNewCart(Member member) {
        Cart newCart = Cart.builder()
                .member(member)
                .isPaid(false)
                .items(new ArrayList<>())
                .build();
        return cartRepository.save(newCart);
    }



    // 사용자 ID로 장바구니 조회
    public List<CartItemDto> getCartItems(Long memberId) {
        log.info("멤버 ID {}에서 미결제 카트 아이템 조회", memberId);
        Cart cart = cartRepository.findLatestActiveCartByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));

        return cart.getItems().stream()
                .map(this::convertToCartItemDto)
                .collect(Collectors.toList());
    }

    private CartItemDto convertToCartItemDto(CartItem cartItem) {
        String imageUrl = getFirstImageUrl(cartItem.getItem().getImage());
        String color = cartItem.getVariant() != null ? cartItem.getVariant().getColor() : null;
        String size = cartItem.getSize() != null ? cartItem.getSize().getSize() : null;

        return CartItemDto.builder()
                .itemId(cartItem.getItem().getItemId())
                .productName(cartItem.getItem().getProductName())
                .color(color)
                .size(size)
                .quantity(cartItem.getQuantity())
                .price(cartItem.getItem().getPrice())
                .imageURL(imageUrl)
                .build();
    }

    private String getFirstImageUrl(List<ItemImage> images) {
        // 이미지 리스트 중 첫 번째 이미지의 URL을 반환
        if (images == null || images.isEmpty()) {
            return "이미지가 없습니다";
        }
        return images.get(0).getFilePath();
    }



    // 카트 아이템 수량 업데이트
    public void updateCartItemQuantity(Long memberId, Long itemId, Long variantId, Long sizeId, int quantity) {
        log.info("사용자 ID {}, 아이템 ID {}, 변형 ID {}, 사이즈 ID {}의 수량을 {}로 업데이트합니다.", memberId, itemId, variantId, sizeId, quantity);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByMemberIdAndItemIdAndVariantIdAndSizeId(memberId, itemId, variantId, sizeId);
        CartItem cartItem = cartItemOptional.orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));

        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.INVALID_CART_ITEM_QUANTITY);
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        log.info("사용자 ID {}의 아이템 ID {} 수량 업데이트 완료", memberId, itemId);
    }

    // 카트 아이템 삭제
    @Transactional
    public void removeCartItem(Long memberId, Long itemId, Long variantId, Long sizeId) {
        log.info("사용자 ID {}에서 아이템 ID {}, 변형 ID {}, 사이즈 ID {}를 삭제합니다.", memberId, itemId, variantId, sizeId);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByMemberIdAndItemIdAndVariantIdAndSizeId(memberId, itemId, variantId, sizeId);
        CartItem cartItem = cartItemOptional.orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.deleteCartItemById(itemId);
        log.info("사용자 ID {}의 아이템 ID {}, 변형 ID {}, 사이즈 ID {} 삭제 완료", memberId, itemId, variantId, sizeId);
    }

    // 카트 내 모든 아이템 삭제
    public void clearCart(Long memberId) {
        log.info("사용자 ID {}의 모든 아이템을 삭제합니다.", memberId);
        Optional.ofNullable(cartItemRepository.findByCart_Member_MemberId(memberId))
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CART));

        cartItemRepository.clearCartByMemberId(memberId);
        log.info("사용자 ID {}의 모든 아이템 삭제 완료", memberId);
    }


    public List<CartItem> getUnpaidCartItems(Long memberId) {
        return cartItemRepository.findUnpaidCartItemsByMemberId(memberId);
    }

}
