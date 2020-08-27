package com.assignment.ums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class UmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(UmsApplication.class, args);
	}
}
