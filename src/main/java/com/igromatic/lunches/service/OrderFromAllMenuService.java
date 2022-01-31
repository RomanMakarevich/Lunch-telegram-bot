package com.igromatic.lunches.service;

import com.igromatic.lunches.controller.MenuController;
import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
public class OrderFromAllMenuService {
    private final OrderService orderService;
    private  String logPath;
    static Logger LOGGER;

    @Autowired
    public OrderFromAllMenuService(OrderService orderService, @Value("${spring.logpath}") String logpath) {
        this.orderService = orderService;
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

    public ResponseDTO order(RequestDTO requestDTO) {
        requestDTO.setText(requestDTO.getText().substring(0,1) + requestDTO.getText().substring(2));
        LOGGER.info(requestDTO.toString());
        return orderService.order(requestDTO,true);
    }
}
