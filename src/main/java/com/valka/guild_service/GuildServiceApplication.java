package com.valka.guild_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GuildServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuildServiceApplication.class, args);
	}

}
