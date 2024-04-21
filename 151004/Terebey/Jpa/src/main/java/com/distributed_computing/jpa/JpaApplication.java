package com.distributed_computing.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	ModelMapper modelMapper = new ModelMapper();
	@Bean
	public ModelMapper modelMapper(){
		return modelMapper;
	}

}