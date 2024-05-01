package com.backend.supermeproject.member.controller;


import com.backend.supermeproject.member.dto.RequestJoin;
import com.backend.supermeproject.member.dto.RequestLogin;
import com.backend.supermeproject.member.dto.ResponseLogin;
import com.backend.supermeproject.member.dto.jwt.CustomJwtFilter;
import com.backend.supermeproject.member.service.MemberInfoService;
import com.backend.supermeproject.member.service.MemberJoinService;
import com.backend.supermeproject.member.service.MemberLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public ResponseEntity<Object> join(@RequestBody RequestJoin form) {
        joinService.save(form);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }

    @GetMapping("/member_only")
    public void MemberOnlyUrl() {
        log.info("회원 전용 URL 접근 테스트");
    }


    @GetMapping("/admin_only")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void adminOnlyUrl() {
        log.info("관리자 전용 URL 접근 테스트");
    }
}