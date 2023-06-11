package com.manish.NewsAggregator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
}
