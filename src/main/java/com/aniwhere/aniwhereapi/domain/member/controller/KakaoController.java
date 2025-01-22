package com.aniwhere.aniwhereapi.domain.member.controller;


import com.aniwhere.aniwhereapi.domain.member.dto.KakaoRequestDTO;
import com.aniwhere.aniwhereapi.domain.member.dto.LoginResponseDTO;
import com.aniwhere.aniwhereapi.domain.member.dto.MemberAuthDTO;
import com.aniwhere.aniwhereapi.domain.member.service.KakaoService;
import com.aniwhere.aniwhereapi.domain.member.service.MemberService;
import com.aniwhere.aniwhereapi.props.JwtProps;
import com.aniwhere.aniwhereapi.util.CookieUtil;
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
@RequestMapping("/api/member/kakao")
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;
    private final MemberService memberService;
    private final JwtProps jwtProps;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> kakaoLogin(@Valid @RequestBody KakaoRequestDTO requestDTO,
                                                       HttpServletResponse response) {

        log.info("Kakao login request: {}", requestDTO);

        MemberAuthDTO memberAuthDTO = kakaoService.getKakaoMember(requestDTO);
        //카카오 에서 받은 유저 정보를 조회 후(옶으면 회원가입 처리)  유저인증 객체로 만듦

        Map<String, Object> kakaoClaims = kakaoService.getKakaoClaims(memberAuthDTO);
        String refreshToken = kakaoClaims.get("refresh_token").toString();
        String accessToken = kakaoClaims.get("access_token").toString();
        //인증 클레임으로 만들고 쿠키 + 토큰과 함께 보낸다
        CookieUtil.setTokenCookie(response, "refreshToken", refreshToken, jwtProps.getRefreshTokenExpirationPeriod());
        //쿠키랑 같이 보내기

        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .email(kakaoClaims.get("email").toString())
                .name(kakaoClaims.get("name").toString())
                .roles((List<String>) kakaoClaims.get("roleNames"))
                .accessToken(accessToken)
                .build();

        log.info("Kakao login response: {}", loginResponseDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }


}
