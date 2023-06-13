package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.constants.NetworkConstants;
import com.manish.NewsAggregator.model.GuardianQuery;
import com.manish.NewsAggregator.model.Query;
import com.manish.NewsAggregator.util.URLBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GuardianApiClientSourceService implements ApiClientSource {

    @Override
    public Mono<? extends Query> getArticle(WebClient webClient, String query, int pageNum) {
        URLBuilder urlBuilder = URLBuilder.builder()
                .baseUrl(NetworkConstants.GUARDIAN_API_BASE_URL)
                .endPoint(NetworkConstants.GUARDIAN_API_SEARCH_ARTICLE_END_POINT)
                .query(query)
                .pageNum(pageNum)
                .apiKey("edf150b5-e597-40ab-887c-f530b37c7a77")
                .build();
        return webClient.get()
                .uri(urlBuilder.toString())
                .retrieve()
                .bodyToMono(GuardianQuery.class);
    }
}
