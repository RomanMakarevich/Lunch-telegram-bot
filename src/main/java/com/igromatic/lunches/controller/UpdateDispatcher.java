package com.igromatic.lunches.controller;

import com.igromatic.lunches.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Component
public class UpdateDispatcher {

    private final CommandProcessor commandProcessor;
    private String logPath;
    static Logger LOGGER;

    @Autowired
    public UpdateDispatcher(CommandProcessor commandProcessor, @Value("${spring.logpath}") String logpath) {
        this.commandProcessor = commandProcessor;
        this.logPath = logpath;
        initLogger();
    }

    private void initLogger() {
        try (FileInputStream fis = new FileInputStream(logPath)) {
            LogManager.getLogManager().readConfiguration(fis);
            LOGGER = Logger.getLogger(MenuController.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public ResponseDTO dispatch(Update update) {
        if (update.hasMessage()) {
            LOGGER.info("if (update.hasMessage())" + update.getMessage().toString());
            return processMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            LOGGER.info("else if (update.hasCallbackQuery())");
            return commandProcessor.processText(update.getCallbackQuery());
        }
        LOGGER.info("else -> new ResponseDTO()");
        return new ResponseDTO();
    }

    public ResponseDTO processMessage(Message message) {
        if (message.hasText()) {
            LOGGER.info("if (message.hasText())" + message.toString());
            return commandProcessor.processText(message);
        }
        LOGGER.info("else -> new ResponseDTO()");
        return new ResponseDTO();
    }
}
