package com.manish.NewsAggregator.services;

import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.Results;
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

    public void storeData(String id, Results data){
        Results results = new Results(id, data.getArticles());
        List<Article> uniqueArticlesInCache = new ArrayList<>();
        data.getArticles().forEach(
                article -> {
                    if (!articleDataRepository.existsByHeadline(article.getHeadline())) {
                        uniqueArticlesInCache.add(article);
                    }
                }
        );
        if (!uniqueArticlesInCache.isEmpty()) {
            results.setArticles(uniqueArticlesInCache);
            cacheDataRepository.save(results);
        }
    }

    public Mono<Results> getCachedDataForQuery(String query, int offset, int limit){
        Results cachedResults = cacheDataRepository.getResultsWithQuery(query, offset, limit);
        return Mono.just(cachedResults);
    }
}
