package com.backend.supermeproject.item.controller;

import com.backend.supermeproject.item.dto.ProductDTO;
import com.backend.supermeproject.item.service.ItemService;
import com.backend.supermeproject.member.jwt.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ProductItemController {
    private final ItemService itemService;

    @PostMapping("/item/add")
    public ResponseEntity<String> uploadController(@RequestParam("files") List<MultipartFile> file,
                                                   @AuthenticationPrincipal MemberInfo member,
                                                   ProductDTO request

    ) {
        itemService.uploadItem(file, member, request);

        return ResponseEntity.ok().body("등록");
    }

}
