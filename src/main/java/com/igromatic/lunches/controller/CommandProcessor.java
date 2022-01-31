package com.igromatic.lunches.controller;

import com.igromatic.lunches.commands.BotCommand;
import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Controller
public class CommandProcessor {
    private final StartService startService;
    private final AskService askService;
    private final OrderService orderService;
    private final OrderFromAllMenuService orderFromAllMenuService;
    private final AuthenticationBotService authenticationService;
    private final SumService sumService;
    private final GetAllMenuService getAllMenuService;
    private final DelService delService;
    private final DeleteService deleteService;
    private final InfoService infoService;
    private  String logPath;
    static Logger LOGGER;

    @Autowired
    public CommandProcessor(StartService startService, AskService askService, OrderService orderService,
                            OrderFromAllMenuService orderFromAllMenuService, AuthenticationBotService authenticationService,
                            SumService sumService, GetAllMenuService getAllMenuService, DelService delService,
                            DeleteService deleteService, InfoService infoService, @Value("${spring.logpath}") String logpath) {
        this.startService = startService;
        this.askService = askService;
        this.orderService = orderService;
        this.orderFromAllMenuService = orderFromAllMenuService;
        this.authenticationService = authenticationService;
        this.sumService = sumService;
        this.getAllMenuService = getAllMenuService;
        this.delService = delService;
        this.deleteService = deleteService;
        this.infoService = infoService;
        this.logPath = logpath;
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

    public ResponseDTO processText(Message message) {

        BotCommand command = BotCommand.toCommand(message.getText());
        LOGGER.info("CALL BotCommand.toCommand WITH " + message.getText() + "/  command = " + command);
        switch (command) {
            case START:
                return startService.start(new RequestDTO(message));
            case PIN:
                return authenticationService.authentication(new RequestDTO(message));
            case ASK:
                return askService.ask();
            case MONEY:
                return sumService.sum(new RequestDTO(message));
            case ALLMENU:
                return getAllMenuService.getAllMenu(new RequestDTO(message));
            case ORDER1:
                return orderService.order(new RequestDTO(message), false);
            case ORDER2:
                return orderService.order(new RequestDTO(message), false);
            case ORDER3:
                return orderService.order(new RequestDTO(message), false);
            case ORDER4:
                return orderService.order(new RequestDTO(message), false);
            case ORDER5:
                return orderService.order(new RequestDTO(message), false);
            case ORDER6:
                return orderService.order(new RequestDTO(message), false);
            case ORDER7:
                return orderService.order(new RequestDTO(message), false);
            case ORDER8:
                return orderService.order(new RequestDTO(message), false);
            case ORDER9:
                return orderService.order(new RequestDTO(message), false);
            case ORDER:
                return orderFromAllMenuService.order(new RequestDTO(message));
            case DEL:
                return delService.del(new RequestDTO(message));
            case DELETE:
                return deleteService.delete(new RequestDTO(message));
            case INFO:
                return infoService.info(new RequestDTO(message));
            case NONE:
            default:
                return new ResponseDTO(message.getChatId(), "Неизвестная команда.");
        }
    }

    public ResponseDTO processText(CallbackQuery message) {

        BotCommand command = BotCommand.toCommand(message.getData());
        LOGGER.info("CALL BotCommand.toCommand WITH " + message.getData() + "/  command = " + command);
        switch (command) {
            case START:
                return startService.start(new RequestDTO(message));
            case PIN:
                return authenticationService.authentication(new RequestDTO(message));
            case ASK:
                return askService.ask();
            case MONEY:
                return sumService.sum(new RequestDTO(message));
            case ALLMENU:
                return getAllMenuService.getAllMenu(new RequestDTO(message));
            case ORDER1:
                return orderService.order(new RequestDTO(message), false);
            case ORDER2:
                return orderService.order(new RequestDTO(message), false);
            case ORDER3:
                return orderService.order(new RequestDTO(message), false);
            case ORDER4:
                return orderService.order(new RequestDTO(message), false);
            case ORDER5:
                return orderService.order(new RequestDTO(message), false);
            case ORDER6:
                return orderService.order(new RequestDTO(message), false);
            case ORDER7:
                return orderService.order(new RequestDTO(message), false);
            case ORDER8:
                return orderService.order(new RequestDTO(message), false);
            case ORDER9:
                return orderService.order(new RequestDTO(message), false);
            case ORDER:
                return orderFromAllMenuService.order(new RequestDTO(message));
            case DEL:
                return delService.del(new RequestDTO(message));
            case DELETE:
                return deleteService.delete(new RequestDTO(message));
            case INFO:
                return infoService.info(new RequestDTO(message));
            case NONE:
            default:
                return new ResponseDTO(message.getMessage().getChatId(), "Неизвестная команда.");
        }
    }
}
