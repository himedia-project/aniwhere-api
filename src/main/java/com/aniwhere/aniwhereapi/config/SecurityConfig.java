package com.aniwhere.aniwhereapi.config;


import com.aniwhere.aniwhereapi.security.filter.JWTCheckFilter;
import com.aniwhere.aniwhereapi.security.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity  // Spring Security 설정을 활성화
@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity  // @PreAuthorize, @Secured, @RolesAllowed 어노테이션을 사용하기 위해 필요
public class SecurityConfig {

    private final JWTCheckFilter jwtCheckFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("security config...............");

//        http
//                .authorizeHttpRequests((authorizedHttpRequests) -> authorizedHttpRequests
//                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll());

        http.authorizeHttpRequests(
                authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/api/member/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/product/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/content/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/test/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/chat/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/crawl/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/category/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/heart/**")).permitAll()
                        // api path에 admin 포함되면 ROLE_ADMIN 권한이 있어야 접근 가능,
                        .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole("ADMIN")
                        .anyRequest().authenticated()
        );



        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        http.sessionManagement(sessionConfig -> {
            sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.csrf(AbstractHttpConfigurer::disable);


        // h2-console, 해당 페이지가 동일한 출처에서만 프레임으로 로드될 수 있도록 설정
        http
                .headers(headers -> {
                    headers.addHeaderWriter(
                            new XFrameOptionsHeaderWriter(
                                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                            )
                    );

                });

        // JWT Check Filter 추가
        http.addFilterBefore(jwtCheckFilter,
                UsernamePasswordAuthenticationFilter.class);
        // exceptionHandler, 접근 거부 핸들러 추가
        http.exceptionHandling(config -> {
            config.accessDeniedHandler(new CustomAccessDeniedHandler());
        });

        return http.build();
    }


    // 무시할 경로 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        new AntPathRequestMatcher("/favicon.ico"),
                        new AntPathRequestMatcher("/v2/api-docs"),
                        new AntPathRequestMatcher("/swagger-resources/**"),
                        new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/webjars/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/h2-console/**")
                );
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // 출처 설정 (모든 출처 허용)
//        configuration.setAllowedOriginPatterns(Arrays.asList("*"));  // localhost:3000 -> 허용
        configuration.setAllowedOrigins(Arrays.asList( //카카오api 만 넣으면 됨
        ));
        // 허용할 메서드 설정
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 허용할 헤더 설정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        // 자격 증명 허용 설정 (쿠키 등)
        configuration.setAllowCredentials(true);
        // content-disposition 허용 설정 -> excel 파일 다운로드시, 제목노출을 위해 필요!
        configuration.setExposedHeaders(Arrays.asList("Content-Disposition"));
        // CORS 설정을 특정 경로에 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
