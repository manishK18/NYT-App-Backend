package com.manish.NewsAggregator;

import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class NewsAggregatorApplication {
	@Bean
	public WebClient getWebClient(){
		return WebClient.create();
	}

	@Bean
	public JaccardSimilarity getJaccardSimilarity() {
		return new JaccardSimilarity();
	}

	public static void main(String[] args) {
		SpringApplication.run(NewsAggregatorApplication.class, args);
	}

}
