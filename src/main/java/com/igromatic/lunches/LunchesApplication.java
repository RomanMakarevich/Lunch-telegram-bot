package com.igromatic.lunches;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class LunchesApplication {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        SpringApplication.run(LunchesApplication.class, args);
    }

}
