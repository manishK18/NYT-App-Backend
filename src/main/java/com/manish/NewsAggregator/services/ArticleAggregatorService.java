package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.model.ApiResponse;
import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.Results;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.*;

@Service
public class ArticleAggregatorService {

    private static final double THRESHOLD = 0.9;
    @Autowired
    private NYTApiClientService nytApiClientService;
    @Autowired
    private GuardianApiClientService guardianApiClientService;
    @Autowired
    private JaccardSimilarity jaccardSimilarity;
    @Autowired
    private CacheDataService cacheDataService;

    public Results getAggregatedResults(WebClient webClient, String query, int offset, int limit) {
        Results response = new Results(query, new ArrayList<>());
        ApiResponse nytApiResponse = new ApiResponse(new ArrayList<>());
        ApiResponse guardianApiResponse = new ApiResponse(new ArrayList<>());
        if (limit == 0) return response;
        int pageNum = (offset / limit) + 1;

        Mono<? extends Results> nytResults = nytApiClientService.getArticle(webClient, query, pageNum);
        Mono<? extends Results> guardianResults = guardianApiClientService.getArticle(webClient, query, pageNum);

        nytResults.doOnSuccess(
                nytResponse -> nytApiResponse.getArticleList().addAll(nytResponse.getResults())
        ).onErrorResume(
                throwable -> {
                    System.out.println("ON ERROR - NYT");
                    return Mono.empty();
                }
        ).subscribe();

        guardianResults.doOnSuccess(
                guardianResponse -> guardianApiResponse.getArticleList().addAll(guardianResponse.getResults())
        ).onErrorResume(
                throwable -> {
                    System.out.println("ON ERROR - GUARDIAN");
                    return Mono.empty();
                }
        ).subscribe();

        Mono<? extends Tuple2<? extends Results, ? extends Results>> combined = Mono.zip(nytResults, guardianResults)
                .onErrorResume(
                        throwable -> Mono.empty()
                );
        combined.subscribe();
        combined.block();
        // Set result with non-duplicate
        response.setResults(
                removeDuplicates(nytApiResponse, guardianApiResponse)
        );

        if (Objects.nonNull(response.getResults())) {
            cacheDataService.storeData(query, response);
        } else {
            cacheDataService.getCachedDataForQuery(query, offset, limit).doOnSuccess(
                    cachedResponse -> response.getResults().addAll(cachedResponse.getResults())
            ).onErrorResume(
                    throwable -> Mono.empty()
            );
        }
        return response;
    }

    private List<Article> removeDuplicates(ApiResponse nytApiResponse, ApiResponse guardianApiResponse) {
        List<Article> nytArticles = nytApiResponse.getArticleList();
        Set<Article> guardianArticles = new HashSet<>(guardianApiResponse.getArticleList());
        List<Article> uniqueArticles = new ArrayList<>(nytArticles);

        for (Article nytArticle : nytArticles) {
            guardianArticles.removeIf(
                    guardianArticle -> areArticlesSimilar(nytArticle, guardianArticle)
            );
        }
        uniqueArticles.addAll(guardianArticles);
        return uniqueArticles;
    }

    private boolean areArticlesSimilar(Article nytArticle, Article guardianArticle) {
        double similarity = jaccardSimilarity.apply(nytArticle.getHeadline(), guardianArticle.getHeadline());
        return similarity > THRESHOLD;
    }
}
