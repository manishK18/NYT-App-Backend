package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.model.NYTResults;
import com.manish.NewsAggregator.model.Results;
import com.manish.NewsAggregator.util.URLBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NYTApiClientService implements SourceApiClient{

    @Override
    public Mono<? extends Results> getArticle(WebClient webClient, String query, int pageNum) {
        URLBuilder urlBuilder = URLBuilder.builder()
                .baseUrl("https://api.nytimes.com/svc/search/v2/articlesearch.json")
                .query(query)
                .pageNum(pageNum)
                .apiKey("mL3egouwMW570XuOAGbbAB0t0wguOmIC")
                .build();

        return webClient.get()
                .uri(urlBuilder.toString())
                .retrieve()
                .bodyToMono(NYTResults.class);
    }
}

// mL3egouwMW570XuOAGbbAB0t0wguOmIC
// NG6qSWNgnOxNYHldrxthbkoMjmoUYkhG