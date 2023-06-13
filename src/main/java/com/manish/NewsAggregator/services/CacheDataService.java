package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.QueryData;
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

    public void storeData(String queryValue, QueryData data) {
        QueryData exisitingQueryData = cacheDataRepository.getQueryByValue(queryValue);
        final QueryData queryData = exisitingQueryData == null ? new QueryData(queryValue) : exisitingQueryData;

        List<Article> uniqueArticlesInCache = new ArrayList<>();
        data.getArticles().forEach(
                article -> {
                    if (!articleDataRepository.existsByHeadline(article.getHeadline())) {
                        uniqueArticlesInCache.add(article);
                        article.setQueryData(queryData);
                    }
                }
        );
        if (!uniqueArticlesInCache.isEmpty()) {
            queryData.getArticles().addAll(uniqueArticlesInCache);
            cacheDataRepository.save(queryData);
        }
    }

    public Mono<QueryData> getCachedDataForQuery(String query, int offset, int limit) {
        QueryData cachedQueryData = cacheDataRepository.getResultsWithQuery(query, offset, limit);
        return Mono.just(cachedQueryData);
    }
}
