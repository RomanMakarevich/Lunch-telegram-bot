package com.igromatic.lunches.repository;

import com.igromatic.lunches.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    public MenuEntity findByProductName(String productName);

    public List<MenuEntity> findAllByStatus(String status);
}
