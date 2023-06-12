package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.constants.NetworkConstants;
import com.manish.NewsAggregator.model.NYTResults;
import com.manish.NewsAggregator.model.Results;
import com.manish.NewsAggregator.util.URLBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NYTApiClientSourceService implements ApiClientSource {

    @Override
    public Mono<? extends Results> getArticle(WebClient webClient, String query, int pageNum) {
        URLBuilder urlBuilder = URLBuilder.builder()
                .baseUrl(NetworkConstants.NYT_API_BASE_URL)
                .endPoint(NetworkConstants.NYT_API_SEARCH_ARTICLE_END_POINT)
                .query(query)
                .pageNum(pageNum)
                .apiKey("NG6qSWNgnOxNYHldrxthbkoMjmoUYkhG")
                .build();

        return webClient.get()
                .uri(urlBuilder.toString())
                .retrieve()
                .bodyToMono(NYTResults.class);
    }
}
