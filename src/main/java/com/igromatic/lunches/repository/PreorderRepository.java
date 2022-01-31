package com.igromatic.lunches.repository;

import com.igromatic.lunches.entity.PreorderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PreorderRepository extends JpaRepository<PreorderEntity, Long> {
    List<PreorderEntity> findByCreatedBetween(LocalDate start, LocalDate end);

    @Query(value = "SELECT p.* FROM preorder p WHERE p.user_id = :user_id AND p.created BETWEEN :start AND :end", nativeQuery = true)
    List<PreorderEntity> findByCreatedBetweenAndIdList(@Param("start") LocalDate start, @Param("end") LocalDate end,@Param("user_id") Long userId);

    @Query(value = "SELECT p.* FROM preorder p WHERE p.user_id = :user_id AND p.created = :date ", nativeQuery = true)
    PreorderEntity findByCreatedBetweenAndId(@Param("date") LocalDate start,@Param("user_id") Long userId);
}
