package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.entity.PreorderEntity;
import com.igromatic.lunches.repository.PreorderRepository;
import com.igromatic.lunches.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DelService {
    private final PreorderRepository preorderRepository;
    private final UserRepository userRepository;

    @Autowired
    public DelService(PreorderRepository preorderRepository, UserRepository userRepository) {
        this.preorderRepository = preorderRepository;
        this.userRepository = userRepository;
    }

    public ResponseDTO del(RequestDTO requestDTO) {

        PreorderEntity order = preorderRepository.findByCreatedBetweenAndId(LocalDate.now(),
                userRepository.findByChatId(requestDTO.getChatID()).getId());
        String answer = "";

        for (int i = 0; i < order.getProducts().size(); i++) {
            answer += "/del_" + (i+1) + ". " + order.getProducts().get(i).getProductName() + "\n";
        }

        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setResponse(answer);
        responseDTO.setChatId(requestDTO.getChatID());
        return responseDTO;
    }
}
