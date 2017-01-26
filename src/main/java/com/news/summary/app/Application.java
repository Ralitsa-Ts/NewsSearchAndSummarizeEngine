package com.news.summary.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.news.summary.utils.Summarizer;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("com.news.summary")
@EnableJpaRepositories("com.news.summary")
@ComponentScan("com.news.summary")
public class Application {

	public static void main(String[] args) {
		System.out.print(Summarizer.summarize("The eight men fled in.\n\r\nPresiding judge Giorgos Sakkas said the men were unlikely to receive a fair trial in Turkey."));
		SpringApplication.run(Application.class, args);
	}
}