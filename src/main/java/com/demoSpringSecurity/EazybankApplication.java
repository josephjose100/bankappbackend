package com.demoSpringSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@EnableWebSecurity(debug=true)
@SpringBootApplication
@EnableMethodSecurity(prePostEnabled=true,securedEnabled=true,jsr250Enabled=true)
public class EazybankApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazybankApplication.class, args);
	}

}
