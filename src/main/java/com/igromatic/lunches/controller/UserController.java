package com.igromatic.lunches.controller;

import com.igromatic.lunches.dto.UserDTO;
import com.igromatic.lunches.entity.UserEntity;
import com.igromatic.lunches.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/lunch/users/")
public class UserController {

    private final UserService userService;
    private String logPath;
    static Logger LOGGER;

    @Autowired
    public UserController(UserService userService, @Value("${spring.logpath}") String logpath) {
        this.userService = userService;
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
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO) {
        LOGGER.info(userDTO.toString());
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public UserDTO getUserById(@PathVariable(name = "id") Long id) {
        LOGGER.info(id.toString());
        return userService.getById(id);
    }

    @GetMapping(value = "")
    public List<UserDTO> getUsers() {
        LOGGER.info("Call getUsers()");
        return userService.getAll();
    }


    @PutMapping(value = "{id}/money/")
    public UserDTO setMoneyById(@PathVariable(name = "id") Long id, @RequestHeader("money") BigDecimal money) {
        LOGGER.info(id.toString() + ", " + money);
        return userService.setMoneyById(id, money);
    }

    @PutMapping(value = "{id}/status/")
    public UserDTO setUserStatus(@PathVariable(name = "id") Long id) {
        LOGGER.info(id.toString());
        return userService.setStatus(id);
    }
}
