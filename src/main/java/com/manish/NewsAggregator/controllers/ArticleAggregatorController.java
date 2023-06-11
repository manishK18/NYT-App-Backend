package com.manish.NewsAggregator.controllers;

import com.manish.NewsAggregator.model.Results;
import com.manish.NewsAggregator.services.ArticleAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/news")
public class ArticleAggregatorController {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ArticleAggregatorService articleAggregatorService;

    @GetMapping("/search")
    public Results getSearchResults(
            @RequestParam("query") String query,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit)
    {
        return articleAggregatorService.getAggregatedResults(webClient, query, offset, limit);
    }
}
