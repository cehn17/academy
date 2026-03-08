package com.cehn17.academy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Academy REST API",
                version = "1.0",
                description = "Backend system for academic management with Spring Boot 3, JWT security, and hybrid persistence (MySQL & MongoDB).",
                contact = @Contact(name = "Cesar Niveyro", url = "https://github.com/cehn17")
        )
)
@SpringBootApplication
public class AcademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademyApplication.class, args);
	}

}
