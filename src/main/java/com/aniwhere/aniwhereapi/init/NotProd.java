package com.aniwhere.aniwhereapi.init;

import com.aniwhere.aniwhereapi.domain.member.entity.Member;
import com.aniwhere.aniwhereapi.domain.member.enums.MemberRole;
import com.aniwhere.aniwhereapi.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
//@Profile({"!prod"})
@RequiredArgsConstructor
public class NotProd {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public CommandLineRunner init() {
        return (args) -> {
            log.info("init data...");

            if (memberRepository.count() > 0) {
                return;
            }

            Member member = Member.builder()
                    .email("test@test.com")
                    .name("test")
                    .password(passwordEncoder.encode("1234"))
                    .phone("010-1234-5678")
                    .delFlag(false)
                    .build();

            member.addRole(MemberRole.ADMIN);

            Member savedMember = memberRepository.save(member);


//            List<Test> testList = testRepository.findAll();
//            log.info("testList: {}", testList);
//
//            user.addTestList(testList);
        };
    }
}
