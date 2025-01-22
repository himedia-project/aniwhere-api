package com.aniwhere.aniwhereapi.domain.member.service;

import com.aniwhere.aniwhereapi.domain.member.dto.KakaoRequestDTO;
import com.aniwhere.aniwhereapi.domain.member.dto.MemberAuthDTO;
import com.aniwhere.aniwhereapi.domain.member.entity.Member;

import java.util.Map;

public interface KakaoService {
    MemberAuthDTO getKakaoMember(KakaoRequestDTO requestDTO);

    Map<String, Object> getKakaoClaims(MemberAuthDTO memberAuthDTO);

    Member joinKakaoMember(KakaoRequestDTO requestDTO);


    MemberAuthDTO convertToMemberAuthDTO(Member member);

}
