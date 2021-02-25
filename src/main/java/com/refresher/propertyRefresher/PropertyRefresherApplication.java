package com.refresher.propertyRefresher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PropertyRefresherApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyRefresherApplication.class, args);
	}

}
