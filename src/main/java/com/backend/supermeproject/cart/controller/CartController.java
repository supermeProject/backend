package com.backend.supermeproject.cart.controller;

import com.backend.supermeproject.cart.dto.CartDto;
import com.backend.supermeproject.cart.dto.CartItemDto;
import com.backend.supermeproject.cart.service.CartService;
import com.backend.supermeproject.member.jwt.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name="장바구니 api", description = "장바구니 기능 카트 api")
@RequestMapping("/api/carts")
public class CartController {
       private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 항목 추가
    @Operation(summary = "장바구니 항목 추가하는 API 엔드포인트")
    @PostMapping("/additem")
    public ResponseEntity<String> addCartItem(
            @RequestBody CartItemDto cartItemDto,
            @AuthenticationPrincipal MemberInfo member
    ) {
        Long memberId = member.getMember().getMemberId();
        try {
            cartService.addCartItem(memberId, cartItemDto);
            cartService.updateTotalPrice(memberId);
            return ResponseEntity.ok("장바구니에 상품이 추가되었습니다.");
        } catch (Exception e) {
        return ResponseEntity.badRequest().body("상품 추가에 실패했습니다: " + e.getMessage());
         }
    }

    // 장바구니 조회
    @Operation(summary = "장바구니 조회하는 api 엔드포인트")
    @GetMapping("/info")
    public ResponseEntity<CartDto> getCartByMemberId(
            @AuthenticationPrincipal MemberInfo member) {
        Long memberId = member.getMember().getMemberId();
        CartDto cartDto = cartService.getCartByMemberId(memberId);
        return ResponseEntity.ok(cartDto);
    }

    // 장바구니 항목 수량 변경
    @Operation(summary = "장바구니 항목의 특정 사이즈와 컬러의 수량을 변경하는 API 엔드포인트")
    @PutMapping("/update/{itemId}/variants/{variantId}/sizes/{sizeId}")
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Long itemId,
            @PathVariable Long variantId,
            @PathVariable Long sizeId,
            @RequestParam Integer quantity,
            @AuthenticationPrincipal MemberInfo member) {
        if (quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().body("유효한 수량을 입력해주세요.");
        }
        Long memberId = member.getMember().getMemberId();
        try {
            cartService.updateCartItemQuantity(memberId, itemId, variantId, sizeId, quantity);
            cartService.updateTotalPrice(memberId);
            return ResponseEntity.ok("아이템의 수량이 업데이트 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 장바구니 항목 삭제
    @Operation(summary = "특정 아이템의 특정 변형과 사이즈를 장바구니에서 삭제하는 API 엔드포인트")
    @DeleteMapping("/remove/{itemId}/variants/{variantId}/sizes/{sizeId}")
    public ResponseEntity<?> removeCartItem(
            @PathVariable Long itemId,
            @PathVariable Long variantId,
            @PathVariable Long sizeId,
            @AuthenticationPrincipal MemberInfo member) {
        Long memberId = member.getMember().getMemberId();
        try {
            cartService.removeCartItem(memberId, itemId, variantId, sizeId);
            cartService.updateTotalPrice(memberId);
            return ResponseEntity.ok("선택한 아이템 변형이 장바구니에서 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("상품 삭제 실패: " + e.getMessage());
        }
    }

    // 장바구니 비우기
    @Operation(summary = "장바구니 비우는 api 엔드포인트")
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal MemberInfo member) {
        Long memberId = member.getMember().getMemberId();
        try {
            cartService.clearCart(memberId);
            return ResponseEntity.ok("장바구니가 비워졌습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("장바구니 비우기 실패: " + e.getMessage());
        }
    }

}
