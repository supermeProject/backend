package com.backend.supermeproject.item.service;

import com.backend.supermeproject.global.exception.BusinessException;
import com.backend.supermeproject.global.exception.ErrorCode;
import com.backend.supermeproject.image.ImageEntity.ItemImage;
import com.backend.supermeproject.image.repository.ItemImageRepository;
import com.backend.supermeproject.image.service.ImageUploadUtil;
import com.backend.supermeproject.item.dto.ProductDTO;
import com.backend.supermeproject.member.entity.Member;
import com.backend.supermeproject.member.jwt.MemberInfo;
import com.backend.supermeproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemImageRepository itemImageRepository;
    private final MemberRepository memberRepository;

    public String uploadItem(List<MultipartFile> file, MemberInfo member, ProductDTO request) {
        Member user = memberRepository.findById(member.getMember().getMemberId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        //이미지 저장
        ImageUploadUtil.uploadImages(file, member.getMember().getMemberId(), itemImageRepository);



        return "";
    }
}