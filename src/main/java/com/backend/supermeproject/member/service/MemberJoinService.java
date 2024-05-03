package com.backend.supermeproject.member.service;

import com.backend.supermeproject.global.role.Gender;
import com.backend.supermeproject.image.ImageEntity.ItemImage;
import com.backend.supermeproject.image.repository.ItemImageRepository;
import com.backend.supermeproject.image.service.ImageUploadUtil;
import com.backend.supermeproject.member.dto.RequestJoin;
import com.backend.supermeproject.member.entity.Member;
import com.backend.supermeproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberJoinService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ItemImageRepository itemImageRepository;

    @Transactional
    public void save(RequestJoin request, List<MultipartFile> files) {
        String password = passwordEncoder.encode(request.password());

        Member member = Member.builder()
                .name(request.name())
                .email(request.email())
                .password(password)
                .phoneNumber(request.phoneNumber())
                .country(request.country())
                .address(request.address())
                .postcode(request.postcode())
                .gender(Gender.valueOf(request.gender()))
                .build();


        Member savedMember = memberRepository.save(member);

        boolean hasNonEmptyFile = files.stream().anyMatch(file -> !file.isEmpty());

        if (hasNonEmptyFile) {
            ImageUploadUtil.uploadImages(files, savedMember.getMemberId(), itemImageRepository);
        } else {
            String filePath = System.getProperty("user.dir") + "/src/main/resources/static/files/avatar.png";
            savedMember.profileImage(filePath);
        }
    }


}
