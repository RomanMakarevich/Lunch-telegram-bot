package com.igromatic.lunches.dto;

import com.igromatic.lunches.entity.MenuEntity;
import com.igromatic.lunches.entity.PreorderEntity;
import com.igromatic.lunches.entity.ProductEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;

    private LocalDate created;

    private UserDTO user_id;

    private List<ProductDTO> products;

    public static OrderDTO toDTO(PreorderEntity preorderEntity) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(preorderEntity.getId());
        orderDTO.setCreated(preorderEntity.getCreated());
        orderDTO.setUser_id(UserDTO.toDTO(preorderEntity.getUser_id()));

        ArrayList<ProductDTO> products = new ArrayList<>();
        for (int i = 0; i < preorderEntity.getProducts().size(); i++) {
            products.add(ProductDTO.toDTO(preorderEntity.getProducts().get(i)));
        }
        orderDTO.setProducts(products);

        return orderDTO;
    }

    public static PreorderEntity toEntity(OrderDTO orderDTO) {
        PreorderEntity preorderEntity = new PreorderEntity();
        preorderEntity.setId(orderDTO.getId());
        preorderEntity.setUser_id(UserDTO.toEntity(orderDTO.getUser_id()));
        preorderEntity.setCreated(orderDTO.getCreated());

        ArrayList<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < orderDTO.getProducts().size(); i++) {
            products.add(ProductDTO.toEntity(orderDTO.products.get(i)));
        }

        preorderEntity.setProducts(products);

        return preorderEntity;
    }
}

