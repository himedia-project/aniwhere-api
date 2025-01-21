package com.aniwhere.aniwhereapi.domain.product.repository;

import com.aniwhere.aniwhereapi.domain.product.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tag t WHERE t.name = :tag")
    boolean existsByName(@Param("tag") String tag);

    @Query("SELECT t FROM Tag t WHERE t.name = :tag")
    Tag findByName(@Param("tag") String tag);
}
