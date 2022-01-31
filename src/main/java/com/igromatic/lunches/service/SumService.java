package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class SumService {
    private final UserRepository userRepository;

    @Autowired
    public SumService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseDTO sum(RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setResponse(userRepository.findByChatId(requestDTO.getChatID()).getMoney() + "p.");
        responseDTO.setChatId(requestDTO.getChatID());
        return responseDTO;
    }
}
