package com.manish.NewsAggregator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

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
