package com.mahi.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

}