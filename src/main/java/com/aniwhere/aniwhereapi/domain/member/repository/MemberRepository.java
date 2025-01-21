package com.aniwhere.aniwhereapi.domain.member.repository;


import com.aniwhere.aniwhereapi.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"memberRoleList"})
    @Query("select m from Member m where m.email = :email")
    Optional<Member> getWithRoles(@Param("email") String email);

    @Query("select m from Member m where m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);
}
