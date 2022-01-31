package com.igromatic.lunches.repository;

import com.igromatic.lunches.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "SELECT * FROM product pr WHERE pr.id = (SELECT MAX(p1.id) FROM (SELECT p.* FROM product p WHERE p.product_name = :product_name) p1)", nativeQuery = true)
    ProductEntity findByCreatedAndProductName(@Param("product_name") String productName);

    @Query(value = "SELECT p2.* FROM product p2 WHERE p2.menu_id = :menu_id", nativeQuery = true)
    List<ProductEntity> findLastProductByMenuId(@Param("menu_id") Long menuId);
}
