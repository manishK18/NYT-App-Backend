package com.manish.NewsAggregator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
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
