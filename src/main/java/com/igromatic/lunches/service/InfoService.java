package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.entity.UserEntity;
import com.igromatic.lunches.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoService {
    private final UserRepository userRepository;

    @Autowired
    public InfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseDTO info(RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setResponse(requestDTO.getText().substring(5));

        ArrayList<UserEntity> users = new ArrayList(userRepository.findAllByStatus("ACTIVE"));

        List chatsId = new ArrayList();

        for (int i = 0; i < users.size(); i++) {
            chatsId.add(users.get(i).getChatId());
        }
        responseDTO.setChatsId(chatsId);
        return responseDTO;
    }
}
