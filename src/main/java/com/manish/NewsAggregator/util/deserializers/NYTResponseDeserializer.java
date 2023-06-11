package com.manish.NewsAggregator.util.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.Results;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class NYTResponseDeserializer extends DeserializerFactory{
    @Override
    public Results deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<JsonNode> docsIterator = node.get("response").get("docs").iterator();
        ArrayList<Article> articles = new ArrayList<>();
        while (docsIterator.hasNext()) {
            JsonNode item = docsIterator.next();
            Article article = deserializeToArticle(item);
            articles.add(article);
        }
        Results results = new Results();
        results.setResults(articles);
        return results;
    }

    private Article deserializeToArticle(JsonNode item) {
        String headline = item.get("headline").get("main").asText(null);
        String description = item.get("lead_paragraph").asText(null);
        JsonNode multimedia = item.get("multimedia");
        String imageUrl = getImageUrl(multimedia);
        String webUrl = item.get("web_url").asText(null);
        String authorName = item.get("byline").get("original").asText(null);
        String publishedDate = getFormattedDateTime(item.get("pub_date").asText(null));
        String keywords = getKeywords(item.get("keywords"));
        String typeOfMaterial = null;
        if (item.hasNonNull("type_of_material")) {
            typeOfMaterial = item.get("type_of_material").asText(null);
        }



        return Article.builder()
                .id(UUID.randomUUID().toString())
                .headline(headline)
                .description(description)
                .imageUrl(imageUrl)
                .authorName(authorName)
                .webUrl(webUrl)
                .publishedDate(publishedDate)
                .keywords(keywords)
                .typeOfMaterial(typeOfMaterial)
                .build();
    }

    private String getImageUrl(JsonNode multimedia) {
        for (JsonNode image : multimedia) {
            if (image.get("subtype").asText().contentEquals("xlarge")) {
                return "https://www.nytimes.com/" + image.get("url").asText();
            }
        }
        return null;
    }

    private String getKeywords(JsonNode keywords) {
        int MAX_KEYWORDS_LIMIT = 5;
        int keywordsIncluded = 0;
        StringBuilder keywordsText = new StringBuilder();
        if (keywords.isArray()) {
            for (JsonNode keyword : keywords) {
                if (!keywordsText.isEmpty()) keywordsText.append(", ");
                keywordsText.append(keyword.get("value").asText());
                if (keywordsIncluded++ >= MAX_KEYWORDS_LIMIT) break;
            }
        }
        return keywordsText.toString();
    }
}