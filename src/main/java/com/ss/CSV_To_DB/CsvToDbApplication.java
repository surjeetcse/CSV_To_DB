package com.ss.CSV_To_DB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration(value = "application.properties")
public class CsvToDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvToDbApplication.class, args);
	}

}
