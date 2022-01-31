package com.igromatic.lunches.controller;

import com.igromatic.lunches.LunchesApplication;
import com.igromatic.lunches.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Component
public class BotController extends TelegramLongPollingBot {

    static Logger LOGGER;
    private  String logPath;

    private final UpdateDispatcher updateDispatcher;

    private String botUserName;
    private String botToken;

    @Autowired
    public BotController(UpdateDispatcher updateDispatcher, @Value("${spring.logpath}") String logpath,
                         @Value("${spring.botusername}") String botUserName,
                         @Value("${${spring.bottocken}}") String botToken) {

        this.updateDispatcher = updateDispatcher;
        this.logPath = logpath;
        this.botUserName = botUserName;
        this.botToken = botToken;
        initLogger();
    }

    private void initLogger (){
        try (FileInputStream fis = new FileInputStream(logPath)) {
            LogManager.getLogManager().readConfiguration(fis);
            LOGGER = Logger.getLogger(MenuController.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.info("---------->Call updateDispatcher.dispatch" + update.toString());
        sendMesg(updateDispatcher.dispatch(update));
    }

    public void sendMesg(ResponseDTO response) {
        try {
            SendMessage sendMessage = new SendMessage();

            if (response.getChatsId() == null) {
                sendMessage.setChatId(String.valueOf(response.getChatId()));
                sendMessage.setText(response.getResponse());
//        sendMessage.setParseMode(ParseMode.MARKDOWN);
                if (response.getInlineKeyboardMarkup() != null) {
                    sendMessage.setReplyMarkup(response.getInlineKeyboardMarkup());
                }
                LOGGER.info("SendMessage ONE: " + sendMessage.getChatId() + "," + sendMessage.getText());
                execute(sendMessage);

            } else {
                for (int i = 0; i < response.getChatsId().size(); i++) {
                    sendMessage.setChatId(String.valueOf(response.getChatsId().get(i)));
                    sendMessage.setText(response.getResponse());
//                sendMessage.setParseMode(ParseMode.MARKDOWN);
                    if (response.getInlineKeyboardMarkup() != null) {
                        sendMessage.setReplyMarkup(response.getInlineKeyboardMarkup());

                    }
                    LOGGER.info("SendMessage MANY: " + sendMessage.getChatId() + "," + sendMessage.getText());
                    execute(sendMessage);
                }
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
