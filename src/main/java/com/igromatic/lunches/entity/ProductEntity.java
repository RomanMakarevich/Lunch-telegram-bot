package com.igromatic.lunches.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_id")
    private  Long menuId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "cost")
    private BigDecimal cost;

    @CreatedDate
    @Column(name = "created")
    private LocalDate created;

    public static ProductEntity menuToProduct(MenuEntity menuEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setMenuId(menuEntity.getId());
        productEntity.setProductName(menuEntity.getProductName());
        productEntity.setCost(menuEntity.getCost());
        productEntity.setCreated(LocalDate.now());

        return productEntity;
    }
}
