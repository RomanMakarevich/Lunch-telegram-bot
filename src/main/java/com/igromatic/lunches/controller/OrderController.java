package com.igromatic.lunches.controller;

import com.igromatic.lunches.dto.OrderDTO;
import com.igromatic.lunches.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/lunch/orders/")
public class OrderController {

    private final OrderService orderService;
    private String logPath;
    static Logger LOGGER;

    @Autowired
    public OrderController(OrderService orderService, @Value("${spring.logpath}") String logpath) {
        this.orderService = orderService;
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

    @GetMapping(value = "")
    public List<OrderDTO> getOrdersCreatedBetween(@RequestHeader("firstData") String firstData,
                                                  @RequestHeader("secondData") String secondData) {
        LOGGER.info(firstData + ", " + secondData);
        return orderService.getOrdersCreatedBetween(firstData, secondData);
    }

    @GetMapping(value = "{user_id}")
    public List<OrderDTO> getOrdersCreatedBetweenById(@PathVariable(name = "user_id") Long orderId,
                                                      @RequestHeader("firstData") String firstData,
                                                      @RequestHeader("secondData") String secondData) {
        LOGGER.info(orderId + ", " + firstData + ", " + secondData);
        return orderService.getOrdersCreatedBetweenById(orderId, firstData, secondData);
    }

    @PutMapping("/{id_order}/{id_product}/")
    public OrderDTO addProductToOrder(@PathVariable(name = "id_order") Long orderId,
                                      @PathVariable(name = "id_product") Long productId) {
        LOGGER.info(orderId + ", " + productId);
        return orderService.addProductToOrder(orderId, productId);
    }

    @DeleteMapping("/{id_order}/{id_product}/")
    public OrderDTO delProductFromAnOrder(@PathVariable(name = "id_order") Long orderId,
                                          @PathVariable(name = "id_product") Long productId) {
        LOGGER.info(orderId + ", " + productId);
        return orderService.delProductFromAnOrder(orderId, productId);
    }

    @GetMapping("/sum/")
    public BigDecimal getSumOfOrders(@RequestHeader("firstData") String firstData,
                                     @RequestHeader("secondData") String secondData) {
        LOGGER.info(firstData + ", " + secondData);
        return orderService.getSumOfOrders(firstData, secondData);
    }

    @GetMapping("/sum/{user_id}")
    public BigDecimal getSumOfOrdersByUser(@RequestHeader("firstData") String firstData,
                                           @RequestHeader("secondData") String secondData,
                                           @PathVariable(name = "user_id") Long userId) {
        LOGGER.info(firstData + ", " + secondData + ", " + userId);
        return orderService.getSumOfOrdersByUser(firstData, secondData, userId);
    }

    @GetMapping("/sort/")
    public HashMap<String, Integer> getSortTodayOrders() {
        LOGGER.info("Call getSortTodayOrders()");
        return orderService.getSortTodayOrders();
    }
}
