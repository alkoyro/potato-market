package com.epam.libg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * entry point of application
 */
@SpringBootApplication(scanBasePackages = "com.epam.libg")
public class PotatoBagApp {

    public static void main(String[] args) {
        SpringApplication.run(PotatoBagApp.class, args);
    }
}
