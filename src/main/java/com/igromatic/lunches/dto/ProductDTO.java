package com.igromatic.lunches.dto;

import com.igromatic.lunches.entity.ProductEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductDTO {

    private Long id;

    private Long menuId;

    private String product;

    private BigDecimal cost;

    private LocalDate created;

    public static ProductEntity toEntity (ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setMenuId(productDTO.getId());
        productEntity.setProductName(productDTO.product);
        productEntity.setCost(productDTO.getCost());
        productEntity.setCreated(productDTO.getCreated());

        return productEntity;
    }

    public static ProductDTO toDTO(ProductEntity productEntity){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(productEntity.getId());
        productDTO.setMenuId(productEntity.getMenuId());
        productDTO.setProduct(productEntity.getProductName());
        productDTO.setCost(productEntity.getCost());
        productDTO.setCreated(productEntity.getCreated());

        return productDTO;
    }
}
