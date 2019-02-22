package com.favccxx.amp.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = { "com.favccxx.amp" })
@EnableCaching
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
