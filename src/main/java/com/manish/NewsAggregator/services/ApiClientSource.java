package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.constants.Secrets;
import com.manish.NewsAggregator.model.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface ApiClientSource {
    Mono<? extends QueryData> getArticle(WebClient webClient, String query, int pageNum);
}
