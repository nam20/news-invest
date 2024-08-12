package com.nam20.news_invest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NewsInvestApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsInvestApplication.class, args);
	}

}
