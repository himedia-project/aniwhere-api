package com.aniwhere.aniwhereapi.security.filter;

import com.aniwhere.aniwhereapi.domain.member.dto.MemberAuthDTO;
import com.aniwhere.aniwhereapi.security.CustomUserDetailService;
import com.aniwhere.aniwhereapi.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Component
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailService userDetailService;

    // 해당 필터로직(doFilterInternal)을 수행할지 여부를 결정하는 메서드, SecuirtyConfig 필터를 거치지 않는게 아님!
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        log.info("check uri: " + path);

        // Pre-flight 요청은 필터를 타지 않도록 설정
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        // /api/member/로 시작하는 요청은 필터를 타지 않도록 설정
        if (path.startsWith("/api/member/login") || path.startsWith("/api/member/join")
                || path.startsWith("/api/member/refresh") || path.startsWith("/api/member/logout")
                || path.startsWith("/api/member/kakao") || path.startsWith("/api/member/google")

        ) {
            return true;
        }
        // "/api/product/list" api는 추가하지 말것!

        if (path.startsWith("/api/admin/product/excel/register")) {
            return true;
        }

        // /view 이미지 불러오기 api로 시작하는 요청은 필터를 타지 않도록 설정
        if (path.startsWith("/api/product/view")) {
            return true;
        }
        // 카테고리 리스트 요청은 필터 제외
        if (path.startsWith("/api/category/list")) {
            return true;
        }

        // api/product/{id}/tag/list 요청은 필터 제외
        if (path.startsWith("/api/product/") && path.endsWith("/tag/list")) {
            return true;
        }

        // /api/tag/{id}/product/list 요청은 필터 제외
        if (path.startsWith("/api/tag/") && path.endsWith("/product/list")) {
            return true;
        }

        // health check 요청은 필터를 타지 않도록 설정
        if (path.startsWith("/health")) {
            return true;
        }

        // Swagger UI 경로 제외 설정
        if (path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs")) {
            return true;
        }
        // h2-console 경로 제외 설정
        if (path.startsWith("/h2-console")) {
            return true;
        }

        // /favicon.ico 경로 제외 설정
        if (path.startsWith("/favicon.ico")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("------------------JWTCheckFilter.................");
        log.info("request.getServletPath(): {}", request.getServletPath());
        log.info("..................................................");

        String autHeaderStr = request.getHeader("Authorization");
        log.info("autHeaderStr Authorization: {}", autHeaderStr);

        if ((Objects.equals(autHeaderStr, "Bearer null") || autHeaderStr == null ) && (
                request.getServletPath().startsWith("/api/product/list")
                        || (request.getServletPath().startsWith("/api/product/") && request.getServletPath().endsWith("/detail"))
                        || (request.getServletPath().startsWith("/api/product/") && request.getServletPath().endsWith("/tag/list"))
        )) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            // Bearer accessToken 형태로 전달되므로 Bearer 제거
            String accessToken = autHeaderStr.substring(7);// Bearer 제거
            // 쿠키로 가져와
//            String accessToken = CookieUtil.getTokenFromCookie(request, "accessToken");
            log.info("JWTCheckFilter accessToken: {}", accessToken);

            Map<String, Object> claims = jwtUtil.validateToken(accessToken);

            log.info("JWT claims: {}", claims);

            MemberAuthDTO memberAuthDTO = (MemberAuthDTO) userDetailService.loadUserByUsername((String) claims.get("email"));

            log.info("memberDTO: {}", memberAuthDTO);
            log.info("memberDto.getAuthorities(): {}", memberAuthDTO.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberAuthDTO, memberAuthDTO.getPassword(), memberAuthDTO.getAuthorities());

            // SecurityContextHolder에 인증 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 다음 필터로 이동
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT Check Error...........");
            log.error("e.getMessage(): {}", e.getMessage());

            ObjectMapper objectMapper = new ObjectMapper();
            String msg = objectMapper.writeValueAsString(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }


    }
}
