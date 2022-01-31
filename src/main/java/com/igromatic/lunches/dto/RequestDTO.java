package com.igromatic.lunches.dto;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RequestDTO {
    private Long chatID;
    private String text;
    private String name;
    private Message message;
    private CallbackQuery callbackQuery;

    public RequestDTO(Message message) {
        this.message = message;
        chatID = message.getChatId();
        text = message.getText();
        name = message.getFrom().getFirstName();
    }
    public RequestDTO(CallbackQuery callbackQuery){
        this.callbackQuery = callbackQuery;
        chatID = callbackQuery.getMessage().getChatId();
        text = callbackQuery.getData();
        name = callbackQuery.getMessage().getFrom().getFirstName();
    }

    public Long getChatID() {
        return chatID;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }

    public void setCallbackQuery(CallbackQuery callbackQuery) {
        this.callbackQuery = callbackQuery;
    }
}
