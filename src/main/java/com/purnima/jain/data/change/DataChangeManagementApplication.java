package com.purnima.jain.data.change;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DataChangeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataChangeManagementApplication.class, args);
		log.info("Starting DataChangeManagementApplication..........");
	}

}
