package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.entity.MenuEntity;
import com.igromatic.lunches.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMenuService {
    private final MenuRepository menuRepository;

    public GetAllMenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    public ResponseDTO getAllMenu(RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        List<MenuEntity> productList = menuRepository.findAll();

        String menu = "";

        for (int i = 0; i < productList.size(); i++) {
            menu += "/_" + (i + 1) + "." + productList.get(i).getProductName();
            menu += " (" + productList.get(i).getCost() + "),\n";
        }

        responseDTO.setResponse(menu);
        responseDTO.setChatId(requestDTO.getChatID());
        return responseDTO;
    }
}
