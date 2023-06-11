package com.manish.NewsAggregator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Results {
    @Id
    protected String query;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "results_id")
    protected List<Article> results;
}
