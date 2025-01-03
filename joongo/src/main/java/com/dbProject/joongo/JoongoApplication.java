package com.dbProject.joongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JoongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoongoApplication.class, args);
	}

}
