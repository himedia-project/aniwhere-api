package com.aniwhere.aniwhereapi.init;

import com.aniwhere.aniwhereapi.domain.member.entity.Member;
import com.aniwhere.aniwhereapi.domain.member.enums.MemberRole;
import com.aniwhere.aniwhereapi.domain.member.repository.MemberRepository;
import com.aniwhere.aniwhereapi.domain.product.entity.Category;
import com.aniwhere.aniwhereapi.domain.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
@Profile({"test"})
@RequiredArgsConstructor
public class NotProd {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final CategoryRepository categoryRepository;


    @Bean
    public CommandLineRunner init() {
        return (args) -> {
            log.info("init data...");

            // 데이터가 하나라도 있으면 초기화하지 않음
            if (memberRepository.count() > 0 && categoryRepository.count() > 0) {
                log.info("이미 초기 데이터가 존재합니다.");
                return;
            }

            // Member 초기화
            if (memberRepository.count() == 0) {
                Member member1 = Member.builder()
                        .email("adult@test.com")
                        .name("adult")
                        .password(passwordEncoder.encode("1234"))
                        .phone("010-1234-5678")
                        .delFlag(false)
                        .build();

                member1.addRole(MemberRole.ADULT_USER);

                Member member2 = Member.builder()
                        .email("jammin@test.com")
                        .name("jammin")
                        .password(passwordEncoder.encode("1234"))
                        .phone("010-1234-5678")
                        .delFlag(false)
                        .build();

                member2.addRole(MemberRole.KID);

                memberRepository.saveAll(List.of(member1, member2));
                log.info("Member 초기 데이터 생성 완료");
            }

            // Category 초기화
            if (categoryRepository.count() == 0) {
                List<Category> categories = List.of(
                        Category.builder().name("액션").build(),
                        Category.builder().name("미스테리").build(),
                        Category.builder().name("판타지").build(),
                        Category.builder().name("로맨스").build(),
                        Category.builder().name("성인").build()
                );
                categoryRepository.saveAll(categories);
                log.info("Category 초기 데이터 생성 완료");
            }
        };
    }
}
