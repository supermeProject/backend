package com.backend.supermeproject.member.controller;


import com.backend.supermeproject.member.dto.MyPageDto;
import com.backend.supermeproject.member.dto.RequestJoin;
import com.backend.supermeproject.member.dto.RequestLogin;
import com.backend.supermeproject.member.dto.ResponseLogin;
import com.backend.supermeproject.member.dto.jwt.CustomJwtFilter;
import com.backend.supermeproject.member.jwt.MemberInfo;
import com.backend.supermeproject.member.service.MemberInfoService;
import com.backend.supermeproject.member.service.MemberJoinService;
import com.backend.supermeproject.member.service.MemberLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberLoginService loginService;
    private final MemberInfoService infoService;
    private final MemberJoinService joinService;

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> authorize(@RequestBody RequestLogin requestLogin) {
        ResponseLogin token = loginService.authenticate(requestLogin.email(), requestLogin.password());
        HttpHeaders headers = new HttpHeaders();
        headers.add(CustomJwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.accessToken());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(token);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Object> join(@RequestPart(value = "userInfo") RequestJoin form, @RequestPart(value = "profileImage") List<MultipartFile> files) {
        joinService.save(form, files);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }

    @GetMapping("/myPage")
    public MyPageDto mypage(@AuthenticationPrincipal MemberInfo member) {


        return joinService.getUserMyPage(member);
    }


}