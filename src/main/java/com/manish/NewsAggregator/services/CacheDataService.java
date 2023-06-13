package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.Query;
import com.manish.NewsAggregator.repository.ArticleDataRepository;
import com.manish.NewsAggregator.repository.CacheDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheDataService {

    @Autowired
    private CacheDataRepository cacheDataRepository;

    @Autowired
    private ArticleDataRepository articleDataRepository;

    public void storeData(String queryValue, Query data) {
        Query exisitingQuery = cacheDataRepository.getQueryByValue(queryValue);
        final Query query = exisitingQuery == null ? new Query(queryValue) : exisitingQuery;

        List<Article> uniqueArticlesInCache = new ArrayList<>();
        data.getArticles().forEach(
                article -> {
                    if (!articleDataRepository.existsByHeadline(article.getHeadline())) {
                        uniqueArticlesInCache.add(article);
                        article.setQuery(query);
                    }
                }
        );
        if (!uniqueArticlesInCache.isEmpty()) {
            query.getArticles().addAll(uniqueArticlesInCache);
            cacheDataRepository.save(query);
        }
    }

    public Mono<Query> getCachedDataForQuery(String query, int offset, int limit) {
        Query cachedQuery = cacheDataRepository.getResultsWithQuery(query, offset, limit);
        return Mono.just(cachedQuery);
    }
}
