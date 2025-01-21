package com.aniwhere.aniwhereapi.security;

import com.aniwhere.aniwhereapi.domain.member.dto.MemberAuthDTO;
import com.aniwhere.aniwhereapi.domain.member.entity.Member;
import com.aniwhere.aniwhereapi.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("loadUserByUsername: username: {}", email);

        Member member = memberRepository.getWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException("미존재하는 사용자 email: " + email));

        MemberAuthDTO memberDTO = new MemberAuthDTO(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getMemberRoleList().stream().map(Enum::name).toList());

        log.info("loadUserByUsername result memberDTO: {}", memberDTO);

        return memberDTO;
    }
}
