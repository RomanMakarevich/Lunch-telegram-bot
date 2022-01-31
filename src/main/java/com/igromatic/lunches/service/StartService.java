package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class StartService {

    public ResponseDTO start(RequestDTO requestDTO) {

        return new ResponseDTO(requestDTO.getChatID(), "Привет! Это бот для заказа обедов в компании Игроматик. Для дальнейшей работы введите пароль.");

    }
}
