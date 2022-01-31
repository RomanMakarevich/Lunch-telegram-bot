package com.igromatic.lunches.controller;

import com.igromatic.lunches.dto.MenuDTO;
import com.igromatic.lunches.service.MenuService;
import liquibase.pro.packaged.L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/lunch/menu/")
public class MenuController {
    private String logPath;
    static Logger LOGGER;

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService, @Value("${spring.logpath}") String logpath) {
        this.menuService = menuService;
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

    @PostMapping(value = "")
    public MenuDTO saveProduct(@RequestBody MenuDTO menuDTO) {
        LOGGER.info(menuDTO.toString());
        return menuService.addProduct(menuDTO);
    }

    @GetMapping(value = "{id}")
    public MenuDTO getProductById(@PathVariable(name = "id") Long id) {
        LOGGER.info(id.toString());
        return menuService.getById(id);
    }


    @GetMapping(value = "products/")
    public List<MenuDTO> getAllProducts() {
        LOGGER.info("Call getAllProducts()");
        return menuService.getAll();
    }


    @GetMapping(value = "product/")
    public MenuDTO getByProductName(@RequestBody MenuDTO menuDTO) {
        LOGGER.info(menuDTO.toString());
        return menuService.getByProductName(menuDTO);
    }

    @PutMapping(value = "products/{id}/cost/")
    public MenuDTO setCost(@PathVariable(name = "id") Long id, @RequestHeader BigDecimal cost) {
        LOGGER.info(id.toString() + ", " + cost.toString());
        return menuService.setCost(id, cost);
    }

    @PutMapping(value = "products/{id}/")
    public MenuDTO setProductName(@PathVariable(name = "id") Long id, @RequestBody MenuDTO menuDTO) {
        LOGGER.info(id.toString() + ", " + menuDTO.toString());
        return menuService.setProductName(id, menuDTO);
    }

    @PutMapping(value = "products/{id}/status/")
    public MenuDTO setStatusProduct(@PathVariable(name = "id") Long id) {
        LOGGER.info(id.toString());
        return menuService.setStatus(id);
    }

    @DeleteMapping(value = "products/{id}/")
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        LOGGER.info(id.toString());
        menuService.deleteProduct(id);
    }
}
