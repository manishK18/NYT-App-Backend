package com.manish.NewsAggregator.controllers;

import com.manish.NewsAggregator.constants.Constants;
import com.manish.NewsAggregator.model.Response;
import com.manish.NewsAggregator.services.ArticleAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@RestController
public class ArticleAggregatorController {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ArticleAggregatorService articleAggregatorService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/news/search")
    public ResponseEntity<Response> getSearchResults(
            @RequestParam("query") String query,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit
    )
    {
        if (limit == 0) {
            return ResponseEntity
                    .of(ProblemDetail.forStatusAndDetail(
                            HttpStatus.BAD_REQUEST, Constants.LIMIT_SHOULD_BE_GREATER_THAN_ZERO)
                    ).build();
        }

        Response response =  articleAggregatorService.getAggregatedResults(webClient, query, offset, limit);

        if (response.getArticles().isEmpty()) {
            return ResponseEntity
                    .of(ProblemDetail.forStatusAndDetail(
                            HttpStatus.NO_CONTENT, Constants.NO_DATA_AVAILABLE_FOR_QUERY)
                    ).build();
        }
        response.setStatus(Constants.SUCCESS);
        return ResponseEntity.of(Optional.of(response));
    }
}
