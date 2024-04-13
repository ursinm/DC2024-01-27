package com.example.rw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RwApplication {

    public static void main(String[] args) {
        SpringApplication.run(RwApplication.class, args);
    }

}
