package com.aniwhere.aniwhereapi.domain.member.controller;


import com.aniwhere.aniwhereapi.domain.member.dto.JoinRequestDTO;
import com.aniwhere.aniwhereapi.domain.member.dto.LoginRequestDTO;
import com.aniwhere.aniwhereapi.domain.member.dto.LoginResponseDTO;
import com.aniwhere.aniwhereapi.domain.member.service.MemberService;
import com.aniwhere.aniwhereapi.props.JwtProps;
import com.aniwhere.aniwhereapi.util.CookieUtil;
import com.aniwhere.aniwhereapi.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final JwtProps jwtProps;


    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequestDTO joinRequestDTO) {
        log.info("Join request: {}", joinRequestDTO);
        memberService.join(joinRequestDTO);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody
                                                  LoginRequestDTO loginRequestDTO,
                                                  HttpServletResponse resposnse) {
        log.info("Login request: {}", loginRequestDTO);
        Map<String, Object> loginClaims = memberService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        String refreshToken = loginClaims.get("refresh_token").toString();
        String accessToken = loginClaims.get("access_token").toString();

        CookieUtil.setTokenCookie(resposnse,"refreshToken",refreshToken,jwtProps.getRefreshTokenExpirationPeriod());

        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .email(loginClaims.get("email").toString())
                .name(loginClaims.get("name").toString())
                .roles((List<String>) loginClaims.get("roleNames"))
                .accessToken(accessToken)
                .build();

        log.info("loginResponseDTO: {}", loginResponseDTO);
        // 로그인 성공시, accessToken, email, name, roles 반환
        return ResponseEntity.ok(loginResponseDTO);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        log.info("logout");
        // accessToken은 react 내 redux 상태 지워서 없앰
        // 쿠키 삭제
        CookieUtil.removeTokenCookie(response, "refreshToken");

        return ResponseEntity.ok("logout success!");
    }


}
