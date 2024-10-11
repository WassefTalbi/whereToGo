package com.esprit.actualiteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ActualiteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActualiteServiceApplication.class, args);
    }

}
