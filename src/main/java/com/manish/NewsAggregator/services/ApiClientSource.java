package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.model.Query;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface ApiClientSource {
    Mono<? extends Query> getArticle(WebClient webClient, String query, int pageNum);
}
