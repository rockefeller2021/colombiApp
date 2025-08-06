package com.colombia.auth_api;



import com.colombia.auth_api.service.DataInitializerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(DataInitializerService dataInitializerService) {
        return args -> {
            dataInitializerService.initialize();
        };
    }
}