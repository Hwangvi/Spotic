package com.backSpotic.backSpotic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.backSpotic.backSpotic")
public class BackSpoticApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackSpoticApplication.class, args);
    }
}