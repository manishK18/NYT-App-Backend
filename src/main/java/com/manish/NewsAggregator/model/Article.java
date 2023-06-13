package com.manish.NewsAggregator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Article {
    @Id
    private String id;
    private String imageUrl;
    private String headline;
    @Lob
    private String description;
    private String publishedDate;
    private String keywords;
    private String webUrl;
    private String authorName;
    private String typeOfMaterial;
    @ManyToOne
    @JoinColumn(name = "query_id")
    @JsonIgnore
    protected Query query;
}
