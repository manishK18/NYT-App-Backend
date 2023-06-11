package com.manish.NewsAggregator.repository;

import com.manish.NewsAggregator.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDataRepository extends JpaRepository<Article, String> {
    boolean existsByHeadline(String headline);
}
