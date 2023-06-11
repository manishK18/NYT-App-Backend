package com.manish.NewsAggregator.controllers;

import com.manish.NewsAggregator.model.Results;
import com.manish.NewsAggregator.services.ArticleAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/news")
public class ArticleAggregatorController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ArticleAggregatorService articleAggregatorService;

    @GetMapping("/ping")
    public String ping(){
        return "Healthy!!";
    }

    @GetMapping("/search")
    public Results getSearchResults(
            @RequestParam("query") String query,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit)
    {
        return articleAggregatorService.getAggregatedResults(webClient, query, offset, limit);
    }
}
