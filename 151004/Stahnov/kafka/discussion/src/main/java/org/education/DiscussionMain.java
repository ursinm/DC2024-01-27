package org.education;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiscussionMain {
    public static void main(String[] args) {
        SpringApplication.run(DiscussionMain.class, args);
    }

    ModelMapper modelMapper = new ModelMapper();
    @Bean
    public ModelMapper modelMapper(){
        return modelMapper;
    }
}