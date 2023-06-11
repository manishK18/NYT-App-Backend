package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.model.GuardianResults;
import com.manish.NewsAggregator.model.Results;
import com.manish.NewsAggregator.util.URLBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GuardianApiClientService implements SourceApiClient{
    @Override
    public Mono<? extends Results> getArticle(WebClient webClient, String query, int pageNum) {
        URLBuilder urlBuilder = URLBuilder.builder()
                .baseUrl("https://content.guardianapis.com/search")
                .query(query)
                .pageNum(pageNum)
                .apiKey("edf150b5-e597-40ab-887c-f530b37c7a77")
                .build();
        return webClient.get()
                .uri(urlBuilder.toString())
                .retrieve()
                .bodyToMono(GuardianResults.class);
    }
}
