package com.igromatic.lunches.dto;

import com.igromatic.lunches.entity.MenuEntity;
import com.igromatic.lunches.entity.ProductEntity;
import com.igromatic.lunches.entity.UserEntity;
import liquibase.pro.packaged.U;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuDTO {

    private Long id;

    private String product;

    private BigDecimal cost;

    private String status;

    public static MenuDTO toDTO(MenuEntity menuEntity){
        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setId(menuEntity.getId());
        menuDTO.setProduct(menuEntity.getProductName());
        menuDTO.setCost(menuEntity.getCost());
        menuDTO.setStatus(menuEntity.getStatus());
        return menuDTO;
    }

    public static MenuEntity toEntity(MenuDTO menuDTO){
        MenuEntity menuEntity = new MenuEntity();

        menuEntity.setProductName(menuDTO.getProduct());
        menuEntity.setCost(menuDTO.getCost());
        menuEntity.setStatus(menuDTO.getStatus());

        return menuEntity;
    }
}
