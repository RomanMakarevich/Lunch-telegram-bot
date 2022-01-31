package com.igromatic.lunches.commands;

import com.igromatic.lunches.controller.MenuController;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public enum BotCommand {
    START("/start", "Старт"),
    ASK("/ask", "Спросить про кто  будет обедать"),
    PIN("/1356", "Пароль"),
    MONEY("/sum", "Баланс"),
    ALLMENU("/allMenu", "Всё меню"),
    ORDER1("/1", "Заказ - 1"),
    ORDER2("/2", "Заказ - 2"),
    ORDER3("/3", "Заказ - 3"),
    ORDER4("/4", "Заказ - 4"),
    ORDER5("/5", "Заказ - 5"),
    ORDER6("/6", "Заказ - 6"),
    ORDER7("/7", "Заказ - 7"),
    ORDER8("/8", "Заказ - 8"),
    ORDER9("/9", "Заказ - 9"),
    ORDER("/_n", "Заказ - 1.n"),
    DEL("/del", "Удалить"),
    DELETE("/del_n", "Удалить n"),
    INFO("/info", "Инфо"),
    NONE("", "");

    final String command;
    final String description;
    static Logger LOGGER;
    private String logPath;

    @Autowired
    BotCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    private void initLogger (){
        try (FileInputStream fis = new FileInputStream(logPath)) {
            LogManager.getLogManager().readConfiguration(fis);
            LOGGER = Logger.getLogger(MenuController.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public String getCommand() {
        LOGGER.info(command);
        return command;
    }

    public String getDescription() {
        LOGGER.info(description);
        return description;
    }

    public static BotCommand toCommand(String command) {

        if (command.startsWith("/info")) {
            LOGGER.info(INFO.command);
            return INFO;
        }
        LOGGER.info(command);
        switch (command) {
            case "/start":
                return START;
            case "/ask":
                return ASK;
            case "/1356":
                return PIN;
            case "/allMenu":
                return ALLMENU;
            case "/sum":
                return MONEY;
            case "/del":
                return DEL;
            case "/del_1":
                return DELETE;
            case "/del_2":
                return DELETE;
            case "/del_3":
                return DELETE;
            case "/del_4":
                return DELETE;
            case "/del_5":
                return DELETE;
            case "/del_6":
                return DELETE;
            case "/del_7":
                return DELETE;
            case "/del_8":
                return DELETE;
            case "/del_9":
                return DELETE;
            case "/del_10":
                return DELETE;
            case "/1":
                return ORDER1;
            case "/2":
                return ORDER2;
            case "/3":
                return ORDER3;
            case "/4":
                return ORDER4;
            case "/5":
                return ORDER5;
            case "/6":
                return ORDER6;
            case "/7":
                return ORDER7;
            case "/8":
                return ORDER8;
            case "/9":
                return ORDER9;
            case "/_1":
                return ORDER;
            case "/_2":
                return ORDER;
            case "/_3":
                return ORDER;
            case "/_4":
                return ORDER;
            case "/_5":
                return ORDER;
            case "/_6":
                return ORDER;
            case "/_7":
                return ORDER;
            case "/_8":
                return ORDER;
            case "/_9":
                return ORDER;
            case "/_10":
                return ORDER;
            case "/_11":
                return ORDER;
            case "/_12":
                return ORDER;
            case "/_13":
                return ORDER;
            case "/_14":
                return ORDER;
            case "/_15":
                return ORDER;
            case "/_16":
                return ORDER;
            case "/_17":
                return ORDER;
            case "/_18":
                return ORDER;
            case "/_19":
                return ORDER;
            case "/_20":
                return ORDER;
            case "/_21":
                return ORDER;
            case "/_22":
                return ORDER;
            case "/_23":
                return ORDER;
            case "/_24":
                return ORDER;
            case "/_25":
                return ORDER;
            case "/_26":
                return ORDER;
            case "/_27":
                return ORDER;
            case "/_28":
                return ORDER;
            case "/_29":
                return ORDER;
            case "/_30":
                return ORDER;
            case "/_31":
                return ORDER;
            case "/_32":
                return ORDER;
            case "/_33":
                return ORDER;
            case "/_34":
                return ORDER;
            case "/_35":
                return ORDER;
            case "/_36":
                return ORDER;
            case "/_37":
                return ORDER;
            case "/_38":
                return ORDER;
            case "/_39":
                return ORDER;
            case "/_40":
                return ORDER;
            case "/_41":
                return ORDER;
            case "/_42":
                return ORDER;
            case "/_43":
                return ORDER;
            case "/_44":
                return ORDER;
            case "/_45":
                return ORDER;
            case "/_46":
                return ORDER;
            case "/_47":
                return ORDER;
            case "/_48":
                return ORDER;
            case "/_49":
                return ORDER;
            case "/_50":
                return ORDER;
            default:
                return NONE;
        }
    }
}