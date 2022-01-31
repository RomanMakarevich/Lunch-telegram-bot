package com.igromatic.lunches.dto;

import com.igromatic.lunches.entity.UserEntity;
import liquibase.pro.packaged.U;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private Long chatId;
    private BigDecimal money;

    public static UserDTO toDTO(UserEntity userEntity) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setChatId(userEntity.getChatId());
        userDTO.setMoney(userEntity.getMoney());

        return userDTO;
    }

    public static UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setName(userDTO.getName());
        userEntity.setChatId(userDTO.getChatId());
        userEntity.setMoney(userDTO.getMoney());

        return userEntity;
    }
}
