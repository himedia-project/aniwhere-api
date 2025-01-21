package com.aniwhere.aniwhereapi.domain.member.service;

import com.aniwhere.aniwhereapi.domain.member.dto.JoinRequestDTO;
import com.aniwhere.aniwhereapi.domain.member.dto.MemberAuthDTO;
import com.aniwhere.aniwhereapi.domain.member.entity.Member;
import com.aniwhere.aniwhereapi.domain.member.enums.MemberRole;
import com.aniwhere.aniwhereapi.domain.member.repository.MemberRepository;
import com.aniwhere.aniwhereapi.props.JwtProps;
import com.aniwhere.aniwhereapi.security.CustomUserDetailService;
import com.aniwhere.aniwhereapi.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final JWTUtil jwtUtil;
    private final JwtProps jwtProps;

    private final CustomUserDetailService userDetailService;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> login(String email, String password) {
        MemberAuthDTO memberAuthDTO = (MemberAuthDTO) userDetailService.loadUserByUsername(email);
        log.info("로그인 dto 어쩌구 깽꺵이: " + memberAuthDTO);

        if (!passwordEncoder.matches(password, memberAuthDTO.getPassword())) {
            throw new RuntimeException("비번틀림");

        }

        Map<String, Object> memberClaims = memberAuthDTO.getClaims();

        String accessToken = jwtUtil.generateToken(memberClaims, jwtProps.getAccessTokenExpirationPeriod());
        String refreshToken = jwtUtil.generateToken(memberClaims, jwtProps.getRefreshTokenExpirationPeriod());

        memberClaims.put("access_token", accessToken);
        memberClaims.put("refresh_token", refreshToken);

        return memberClaims;
    }

    @Override
    public void join(JoinRequestDTO joinRequestDTO) {
        memberRepository.findByEmail(joinRequestDTO.getEmail()).ifPresent(member -> {
            throw new IllegalArgumentException("이미 존재하는 회원");
        });
        Long birthday = joinRequestDTO.getBirthday();
        Long year = birthday / 10000;
        if (year >= 2007) {
            Member member = Member.builder()
                    .email(joinRequestDTO.getEmail())
                    .password(passwordEncoder.encode(joinRequestDTO.getPassword()))
                    .name(joinRequestDTO.getName())
                    .birthday(birthday)
                    .phone(joinRequestDTO.getPhone())
                    .memberRoleList(Collections.singletonList(MemberRole.KID))
                    .build();
            memberRepository.save(member);
        } //미성년자
        else {
            Member adultMember = Member.builder()
                    .email(joinRequestDTO.getEmail())
                    .password(passwordEncoder.encode(joinRequestDTO.getPassword()))
                    .name(joinRequestDTO.getName())
                    .birthday(joinRequestDTO.getBirthday())
                    .phone(joinRequestDTO.getPhone())
                    .memberRoleList(Collections.singletonList(MemberRole.ADULT_USER))
                    .build();

            memberRepository.save(adultMember);
        }

    }
}
