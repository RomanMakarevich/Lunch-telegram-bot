package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.UserDTO;
import com.igromatic.lunches.entity.MenuEntity;
import com.igromatic.lunches.entity.UserEntity;
import com.igromatic.lunches.repository.UserRepository;
import liquibase.pro.packaged.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDTO saveUser(UserDTO userDTO) {
        userRepository.save(UserDTO.toEntity(userDTO));
        return userDTO;
    }

    public UserDTO getById(Long id) {
        UserEntity userEntity = new UserEntity();

        userEntity = userRepository.getById(id);
        if (userEntity == null) {
            return null;
        }

        return UserDTO.toDTO(userEntity);
    }

    public List<UserDTO> getAll() {
        List<UserDTO> users = new ArrayList<>();

        List<UserEntity> userEntities = userRepository.findAll();

        for (int i = 0; i < userEntities.size(); i++) {

            users.add(UserDTO.toDTO(userEntities.get(i)));
        }

        return users;
    }

    public UserDTO setMoneyById(Long id, BigDecimal money) {
        UserEntity userEntity = userRepository.getById(id);
        userEntity.setMoney(money);
        userRepository.save(userEntity);

        UserEntity userSaveEntity = userRepository.getById(id);

        return UserDTO.toDTO(userSaveEntity);
    }

    public UserDTO setStatus(Long id) {
        UserEntity userEntity = userRepository.getById(id);

        if (userEntity.getStatus() == "ACTIVE") {
            userEntity.setStatus("NOACTIVE");
        } else {
            userEntity.setStatus("ACTIVE");
        }
        userRepository.save(userEntity);

        return UserDTO.toDTO(userRepository.getById(id));
    }
}
