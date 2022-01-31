package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.entity.UserEntity;
import com.igromatic.lunches.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Data
@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    public ResponseDTO authentication(RequestDTO requestDTO) {

        if (userRepository.findByChatId(requestDTO.getChatID()) != null) {
            System.out.println("Такой пользователь уже есть!");
            return new ResponseDTO(requestDTO.getChatID(), "Такой пользователь уже есть!");
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(requestDTO.getName());
            userEntity.setChatId(requestDTO.getChatID());
            userEntity.setMoney(new BigDecimal("0.0"));
            userEntity.setStatus("ACTIVE");

            userRepository.save(userEntity);
            System.out.println("save");

            return new ResponseDTO(requestDTO.getChatID(), "Вы успешно зарегистрировались!");
        }
    }
}
