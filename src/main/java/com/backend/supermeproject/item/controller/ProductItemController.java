package com.backend.supermeproject.item.controller;

import com.backend.supermeproject.item.dto.AllItemResponse;
import com.backend.supermeproject.item.dto.ItemResponse;
import com.backend.supermeproject.item.dto.ProductDTO;
import com.backend.supermeproject.item.service.ItemService;
import com.backend.supermeproject.member.jwt.MemberInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ProductItemController {
    private final ItemService itemService;

    //item 등록
    @PostMapping("/item/add")
    public ResponseEntity<String> uploadController(@RequestPart("files") List<MultipartFile> file,
                                                   @AuthenticationPrincipal MemberInfo member,
                                                   @RequestPart("request") ProductDTO request

    ) {
        Long memberId = member.getMember().getMemberId();
        itemService.uploadItem(file, memberId, request);

        return ResponseEntity.ok().body("등록");
    }

    //전체조회
    @GetMapping("/category/all")
    public List<AllItemResponse> getItems() {
        return itemService.getAllItem();

    }

    @GetMapping("/product/{id}")
    public ItemResponse getIdItem(@PathVariable Long id) {

        return itemService.getIdItem(id);
    }


}
