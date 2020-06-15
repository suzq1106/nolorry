package com.mikes.nolorry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class NolorryApplication {

	public static void main(String[] args) {

		SpringApplication.run(NolorryApplication.class, args);

	}

}
