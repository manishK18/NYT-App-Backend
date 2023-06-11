package com.manish.NewsAggregator.repository;

import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleDataRepository extends JpaRepository<Article, String> {
    boolean existsByHeadline(String headline);
}
