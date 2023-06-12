package com.manish.NewsAggregator.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Response {
    private String status;
    private String query;
    private List<Article> articles;
    private MetaData metaData;
}
