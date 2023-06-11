package com.manish.NewsAggregator.util;

import lombok.Builder;

@Builder
public class URLBuilder {
    private String baseUrl;
    private String query;
    private int pageNum;
    private String apiKey;

    @Override
    public String toString() {
        return baseUrl + "?q=" + query + "&api-key="+ apiKey + "&page=" + pageNum;
    }
}
