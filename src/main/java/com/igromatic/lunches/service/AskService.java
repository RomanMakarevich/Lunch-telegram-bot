package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.entity.MenuEntity;
import com.igromatic.lunches.entity.UserEntity;
import com.igromatic.lunches.repository.MenuRepository;
import com.igromatic.lunches.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class AskService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public AskService(UserRepository userRepository, MenuRepository menuRepository) {
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    public ResponseDTO ask() {
        ResponseDTO responseDTO = new ResponseDTO();

        List<UserEntity> users = userRepository.findAllByStatus("ACTIVE");
        List<MenuEntity> productList = menuRepository.findAllByStatus("ACTIVE");

        String menu = "";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> buttonsList = new ArrayList<>();

        String menuKeyboard = "";

        List<String> hashes = new ArrayList<>();

        for (int i = 0; i < productList.size(); i++) {
            menu += "/" + (i + 1) + "." + productList.get(i).getProductName();
            menu += " (" + productList.get(i).getCost() + "),\n";

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(String.valueOf(i + 1));

            button.setCallbackData("/" + (i + 1));
            buttonsList.add(button);
        }

        responseDTO.setResponse(menu);

        List<InlineKeyboardButton> buttonsList2 = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("All menu");
        button.setCallbackData("/allMenu");
        buttonsList2.add(button);
        inlineButtons.add(buttonsList);
        inlineButtons.add(buttonsList2);

        inlineKeyboardMarkup.setKeyboard(inlineButtons);

        responseDTO.setInlineKeyboardMarkup(inlineKeyboardMarkup);

        for (int i = 0; i < users.size(); i++) {
            hashes.add(users.get(i).getChatId().toString());
        }

        responseDTO.setChatsId(hashes);

        return responseDTO;
    }
}
