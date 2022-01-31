package com.igromatic.lunches.dto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTO {
    private Long chatId;
    private List<String> chatsId;
    private String response;
    private InlineKeyboardMarkup inlineKeyboardMarkup;

    public ResponseDTO(Long chatId, String response) {
        this.chatId = chatId;
        this.response = response;
    }

    public ResponseDTO(Long chatId, String response, List<String> chatsId) {
        this.chatId = chatId;
        this.response = response;
        this.chatsId = chatsId;
    }

    public ResponseDTO() {
        response = "Неизвестная команда";
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getResponse() {
        return response;
    }

    public List<String> getChatsId() {
        return chatsId;
    }

    public void setChatsId(List<String> chatsId) {
        this.chatsId = chatsId;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return inlineKeyboardMarkup;
    }

    public void setInlineKeyboardMarkup(InlineKeyboardMarkup inlineKeyboardMarkup) {
        this.inlineKeyboardMarkup = inlineKeyboardMarkup;
    }
}
