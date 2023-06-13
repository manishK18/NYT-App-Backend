package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.constants.NetworkConstants;
import com.manish.NewsAggregator.constants.Secrets;
import com.manish.NewsAggregator.model.GuardianQueryData;
import com.manish.NewsAggregator.model.QueryData;
import com.manish.NewsAggregator.util.URLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GuardianApiClientSourceService implements ApiClientSource {
    @Autowired
    private Secrets secrets;

    @Override
    public Mono<? extends QueryData> getArticle(WebClient webClient, String query, int pageNum) {
        URLBuilder urlBuilder = URLBuilder.builder()
                .baseUrl(NetworkConstants.GUARDIAN_API_BASE_URL)
                .endPoint(NetworkConstants.GUARDIAN_API_SEARCH_ARTICLE_END_POINT)
                .query(query)
                .pageNum(pageNum)
                .apiKey(secrets.getGuardianApiKey())
                .build();
        return webClient.get()
                .uri(urlBuilder.toString())
                .retrieve()
                .bodyToMono(GuardianQueryData.class);
    }
}
