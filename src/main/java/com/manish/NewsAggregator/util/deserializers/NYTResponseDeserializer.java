package com.manish.NewsAggregator.util.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.manish.NewsAggregator.constants.Constants;
import com.manish.NewsAggregator.constants.TimeUtils;
import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.QueryData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

@Component
public class NYTResponseDeserializer extends DeserializerFactory{
    @Override
    public QueryData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<JsonNode> docsIterator = node.get("response").get("docs").iterator();
        ArrayList<Article> articles = new ArrayList<>();
        while (docsIterator.hasNext()) {
            JsonNode item = docsIterator.next();
            Article article = deserializeToArticle(item);
            articles.add(article);
        }
        QueryData queryData = new QueryData();
        queryData.setArticles(articles);
        return queryData;
    }

    private Article deserializeToArticle(JsonNode item) {
        String headline = item.get("headline").get("main").asText(null);
        String description = item.get("lead_paragraph").asText(null);
        JsonNode multimedia = item.get("multimedia");
        String imageUrl = getImageUrl(multimedia);
        String webUrl = item.get("web_url").asText(null);
        String authorName = item.get("byline").get("original").asText(null);
        String publishedDate = getFormattedDateTime(
                item.get("pub_date").asText(null),
                TimeUtils.DATE_TIME_INPUT_PATTERN_TYPE_1
        );
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
                return Constants.IMAGE_PATH_PREFIX + image.get("url").asText();
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
