package com.aniwhere.aniwhereapi.domain.member.service;

import com.aniwhere.aniwhereapi.domain.member.dto.JoinRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public interface MemberService {
    Map<String, Object> login(@NotBlank(message = "이메일을 입력해주세요") String email, @NotBlank(message = "패스워드를  입력해주세요") String password);

    void join(@Valid JoinRequestDTO joinRequestDTO);
}
