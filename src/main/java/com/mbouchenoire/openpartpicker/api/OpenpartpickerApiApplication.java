package com.mbouchenoire.openpartpicker.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class OpenpartpickerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenpartpickerApiApplication.class, args);
	}
}
