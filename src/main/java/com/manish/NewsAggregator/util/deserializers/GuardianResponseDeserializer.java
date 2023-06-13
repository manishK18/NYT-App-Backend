package com.manish.NewsAggregator.util.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.manish.NewsAggregator.constants.Constants;
import com.manish.NewsAggregator.constants.TimeUtils;
import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.GuardianQueryData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

@Component
public class GuardianResponseDeserializer extends DeserializerFactory {

    @Override
    public GuardianQueryData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<JsonNode> docsIterator = node.get("response").get("results").iterator();
        ArrayList<Article> articles = new ArrayList<>();
        while (docsIterator.hasNext()) {
            JsonNode item = docsIterator.next();
            Article article = deserializeToArticle(item);
            articles.add(article);
        }
        GuardianQueryData guardianResults = new GuardianQueryData();
        guardianResults.setArticles(articles);
        return guardianResults;
    }

    private Article deserializeToArticle(JsonNode item) {
        String headline = item.get("webTitle").asText(null);
        String webUrl = item.get("webUrl").asText(null);
        String publishedDate = getFormattedDateTime(
                item.get("webPublicationDate").asText(null),
                TimeUtils.DATE_TIME_INPUT_PATTERN_TYPE_2
        );
        String typeOfMaterial = null;
        if (item.hasNonNull("type")) {
            typeOfMaterial = item.get("type").asText(null);
        }
        return Article.builder()
                .id(UUID.randomUUID().toString())
                .headline(headline)
                .description(Constants.DEFAULT_DESCRIPTION)
                .imageUrl(Constants.DEFAULT_IMAGE_URL)
                .authorName(Constants.DEFAULT_NOT_AVAILABLE)
                .webUrl(webUrl)
                .publishedDate(publishedDate)
                .keywords(null)
                .typeOfMaterial(typeOfMaterial)
                .build();
    }
}
