package com.app;

import com.app.service.JwtService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JobApplicationTrackerApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JobApplicationTrackerApplication.class, args);
		System.out.print("Hello World");


	}

}
