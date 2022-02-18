package ru.itmo.businesslogic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="ru.itmo.businesslogic")
public class BusinesslogicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinesslogicApplication.class, args);
    }

}
