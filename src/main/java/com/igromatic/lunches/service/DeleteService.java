package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.entity.PreorderEntity;
import com.igromatic.lunches.entity.UserEntity;
import com.igromatic.lunches.repository.PreorderRepository;
import com.igromatic.lunches.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DeleteService {
    private final PreorderRepository preorderRepository;
    private final UserRepository userRepository;

    public DeleteService(PreorderRepository preorderRepository, UserRepository userRepository) {
        this.preorderRepository = preorderRepository;
        this.userRepository = userRepository;
    }

    public ResponseDTO delete(RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        int number = Integer.parseInt(requestDTO.getText().substring(5));
        UserEntity user = userRepository.findByChatId(requestDTO.getChatID());
        PreorderEntity order = preorderRepository.findByCreatedBetweenAndId(LocalDate.now(), user.getId());

        user.setMoney(user.getMoney().add(order.getProducts().get(number - 1).getCost()));
        order.getProducts().remove(number - 1);

        String answer = "";
        for (int i = 0; i < order.getProducts().size(); i++) {
            answer += ((i != 0) ? " " : "") + order.getProducts().get(i).getProductName();
            answer += " (" + order.getProducts().get(i).getCost() + ((i != order.getProducts().size() - 1) ? ")," : ").");
        }
        answer += " Остаток: " + user.getMoney() + "p.";

        answer += "\n" + (order.getProducts().size() != 0 ? "\n/del" : "\n");
        preorderRepository.save(order);
        userRepository.save(user);
        responseDTO.setResponse(answer);
        responseDTO.setChatId(requestDTO.getChatID());
        return responseDTO;
    }
}
