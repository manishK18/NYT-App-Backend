package com.manish.NewsAggregator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QueryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String queryName;

    @OneToMany(mappedBy = "queryData", cascade = CascadeType.ALL)
    protected List<Article> articles;

    public QueryData(String queryName) {
        this.queryName = queryName;
        articles = new ArrayList<>();
    }

    public QueryData(String queryName, List<Article> articles) {
        this.queryName = queryName;
        this.articles = articles;
    }
}
