package com.soccerleague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SoccerLeagueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccerLeagueApplication.class, args);
	}

}
