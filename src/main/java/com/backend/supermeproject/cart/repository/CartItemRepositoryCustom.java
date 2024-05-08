package com.backend.supermeproject.cart.repository;

import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.entity.CartItem;
import com.backend.supermeproject.item.entity.Item;

import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryCustom {
    List<CartItem> findCartItemsByMemberId(Long memberId);
    CartItem addCartItem(Long memberId, Item item, int quantity);
    void deleteCartItemById(Long itemId);
    void clearCartByMemberId(Long memberId);
    List<Item> findItemsWithImages();
    Optional<CartItem> findCartItemByMemberId(Long memberId);
    List<CartItem> findUnpaidCartItemsByMemberId(Long memberId);
    Optional<CartItem> findByMemberIdAndItemIdAndVariantIdAndSizeId(Long memberId, Long itemId, Long variantId, Long sizeId);


}
