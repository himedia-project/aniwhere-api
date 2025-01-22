package com.aniwhere.aniwhereapi.domain.member.service;

import com.aniwhere.aniwhereapi.domain.member.dto.KakaoRequestDTO;
import com.aniwhere.aniwhereapi.domain.member.dto.MemberAuthDTO;
import com.aniwhere.aniwhereapi.domain.member.entity.Member;
import com.aniwhere.aniwhereapi.domain.member.enums.MemberRole;
import com.aniwhere.aniwhereapi.domain.member.repository.MemberRepository;
import com.aniwhere.aniwhereapi.props.JwtProps;
import com.aniwhere.aniwhereapi.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class KakaoServiceImpl implements KakaoService {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final JwtProps jwtProps;

    @Override
    public MemberAuthDTO getKakaoMember(KakaoRequestDTO requestDTO) {
        Member member = memberRepository.getWithRoles(requestDTO.getEmail()).orElse(null);
//회원이 존재하는지 조회
        if (member == null) {

            //여기사 MemberAuthDTO 로 만들어서 보내기
            Member newKakaoMember = joinKakaoMember(requestDTO);
            return convertToMemberAuthDTO(newKakaoMember);

        } else {
            return convertToMemberAuthDTO(member);
        }
    }

    @Override
    public Map<String, Object> getKakaoClaims(MemberAuthDTO memberAuthDTO) {
        Map<String, Object> kakaoClaims = memberAuthDTO.getClaims();

        String accessToken = jwtUtil.generateToken(kakaoClaims, jwtProps.getAccessTokenExpirationPeriod());
        String refreshToken = jwtUtil.generateToken(kakaoClaims, jwtProps.getRefreshTokenExpirationPeriod());
        kakaoClaims.put("access_token", accessToken);
        kakaoClaims.put("refresh_token", refreshToken);
        return kakaoClaims;

    }

    @Override
    public Member joinKakaoMember(KakaoRequestDTO requestDTO) {
        //카카오아이디 , 이메일만 저장하고 나머지는 가라로 값 넣어서 저장
        Member member = Member.builder()
                .email(requestDTO.getEmail())
                .name("KAKAO_" + requestDTO.getEmail().split("@")[0])
                .password(UUID.randomUUID().toString())
                .birthday(20000101L)
                .phone("00000000000")
                .memberRoleList(Collections.singletonList(MemberRole.ADULT_USER))
                .build();

        memberRepository.save(member);
        return member;
    }

    @Override
    public MemberAuthDTO convertToMemberAuthDTO(Member member) {
        List<String> roleStrings = member.getMemberRoleList().stream().map(MemberRole::name)
                .collect(Collectors.toList());

        return new MemberAuthDTO(
                member.getEmail(),
                member.getPassword(),
                member.getName(), roleStrings
        );
    }
}

