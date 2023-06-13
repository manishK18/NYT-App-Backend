package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.model.*;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ArticleAggregatorService {

  private static final double THRESHOLD = 0.9;
  @Autowired
  private NYTApiClientSourceService nytApiClientService;
  @Autowired
  private GuardianApiClientSourceService guardianApiClientService;
  @Autowired
  private JaccardSimilarity jaccardSimilarity;
  @Autowired
  private CacheDataService cacheDataService;

  public Response getAggregatedResults(WebClient webClient, String query, int offset, int limit) {

    QueryData results = new QueryData(query, new ArrayList<>());
    Response response = Response.builder()
            .query(query)
            .articles(results.getArticles())
            .metaData(new MetaData(offset, limit))
            .build();
    SourceApiResponse nytApiResponse = new SourceApiResponse(new ArrayList<>());
    SourceApiResponse guardianApiResponse = new SourceApiResponse(new ArrayList<>());

    int pageNum = (offset / limit) + 1;

    Mono<? extends QueryData> nytResults = nytApiClientService.getArticle(webClient, query, pageNum);
    Mono<? extends QueryData> guardianResults = guardianApiClientService.getArticle(webClient, query, pageNum);

    nytResults.doOnSuccess(
            nytResponse -> nytApiResponse.getArticleList().addAll(nytResponse.getArticles())
    ).onErrorResume(
            throwable -> {
              System.out.println("NYT Api failed to fetch data due to: " + throwable.getMessage());
              return Mono.empty();
            }
    ).subscribe();

    guardianResults.doOnSuccess(
            guardianResponse -> guardianApiResponse.getArticleList().addAll(guardianResponse.getArticles())
    ).onErrorResume(
            throwable -> {
              System.out.println("Guardian Api failed to fetch data due to: " + throwable.getMessage());
              return Mono.empty();
            }
    ).subscribe();

    Mono<? extends Tuple2<? extends QueryData, ? extends QueryData>> combined = Mono.zip(nytResults, guardianResults)
            .onErrorResume(
                    throwable -> Mono.empty()
            );
    combined.subscribe();
    combined.block();

    // Set result with non-duplicate
    results.setArticles(
            removeDuplicates(nytApiResponse, guardianApiResponse)
    );

    if (Objects.nonNull(results.getArticles())) {
      cacheDataService.storeData(query, results);
    } else {
      cacheDataService.getCachedDataForQuery(query, offset, limit).doOnSuccess(
              cachedResponse -> results.getArticles().addAll(cachedResponse.getArticles())
      ).onErrorResume(
              throwable -> Mono.empty()
      );
    }

    response.setArticles(results.getArticles());
    response.setMetaData(new MetaData(offset + results.getArticles().size(), limit));

    return response;
  }

  private List<Article> removeDuplicates(SourceApiResponse nytSourceApiResponse, SourceApiResponse guardianSourceApiResponse) {
    List<Article> nytArticles = nytSourceApiResponse.getArticleList();
    Set<Article> guardianArticles = new HashSet<>(guardianSourceApiResponse.getArticleList());
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
