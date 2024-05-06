package com.backend.supermeproject.cart.controller;

import com.backend.supermeproject.cart.dto.CartItemDto;
import com.backend.supermeproject.cart.service.CartService;
import com.backend.supermeproject.member.jwt.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * 현재 인증된 사용자의 ID를 가져오는 메서드입니다.
     * SecurityContextHolder에서 Authentication 객체를 추출하여 MemberInfo로부터 memberId를 반환합니다.
     * @return 현재 인증된 사용자의 memberId
     */
    private Long getMemberIdFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        return memberInfo.getMember().getMemberId();
    }

    // 장바구니 항목 추가
    @Operation(summary = "장바구니 항목 추가하는 API 엔드포인트")
    @PostMapping("/additem")
    public ResponseEntity<String> addCartItem(@RequestBody CartItemDto cartItemDto) {
        try {
        Long memberId = getMemberIdFromAuth();
        cartService.addCartItem(memberId, cartItemDto);
            return ResponseEntity.ok("장바구니에 상품이 추가되었습니다.");
        } catch (Exception e) {
        return ResponseEntity.badRequest().body("상품 추가에 실패했습니다: " + e.getMessage());
    }
    }

    // 장바구니 조회
    @Operation(summary = "장바구니 조회하는 api 엔드포인트")
    @GetMapping("/info")
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        Long memberId = getMemberIdFromAuth();
        List<CartItemDto> items = cartService.getCartItems(memberId);
        return ResponseEntity.ok(items);
    }

    // 장바구니 항목 수량 변경
    @Operation(summary = "장바구니 항목 수량 변경하는 api 엔드포인트")
    @PutMapping("/update/{itemId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable Long itemId, @RequestParam(required = false) Integer quantity) {
        if (quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().body("유효한 수량을 입력해주세요.");
        }
        try {
            Long memberId = getMemberIdFromAuth();
            cartService.updateCartItemQuantity(memberId, itemId, quantity);
            return ResponseEntity.ok("수량이 업데이트 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 장바구니 항목 삭제
    @Operation(summary = "장바구니 항목 삭제하는 api 엔드포인트")
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long itemId) {
        try {
            Long memberId = getMemberIdFromAuth();
            cartService.removeCartItem(memberId, itemId);
            return ResponseEntity.ok("상품이 장바구니에서 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("상품 삭제 실패: " + e.getMessage());
        }
    }

    // 장바구니 비우기
    @Operation(summary = "장바구니 비우는 api 엔드포인트")
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        try {
            Long memberId = getMemberIdFromAuth();
            cartService.clearCart(memberId);
            return ResponseEntity.ok("장바구니가 비워졌습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("장바구니 비우기 실패: " + e.getMessage());
        }
    }

}
