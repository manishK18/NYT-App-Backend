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
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String queryName;

    @OneToMany(mappedBy = "query", cascade = CascadeType.ALL)
    protected List<Article> articles;

    public Query(String queryName) {
        this.queryName = queryName;
        articles = new ArrayList<>();
    }

    public Query(String queryName, List<Article> articles) {
        this.queryName = queryName;
        this.articles = articles;
    }
}
