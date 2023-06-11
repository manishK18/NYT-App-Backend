package com.manish.NewsAggregator.util.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.manish.NewsAggregator.model.Article;
import com.manish.NewsAggregator.model.GuardianResults;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

@Component
public class GuardianResponseDeserializer extends DeserializerFactory {
    @Override
    public GuardianResults deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<JsonNode> docsIterator = node.get("response").get("results").iterator();
        ArrayList<Article> articles = new ArrayList<>();
        while (docsIterator.hasNext()) {
            JsonNode item = docsIterator.next();
            Article article = deserializeToArticle(item);
            articles.add(article);
        }
        GuardianResults guardianResults = new GuardianResults();
        guardianResults.setResults(articles);
        return guardianResults;
    }

    private Article deserializeToArticle(JsonNode item) {
        String headline = item.get("webTitle").asText(null);
        String webUrl = item.get("webUrl").asText(null);
        String publishedDate = getFormattedDateTime(item.get("webPublicationDate").asText(null));
        String typeOfMaterial = null;
        if (item.hasNonNull("type")) {
            typeOfMaterial = item.get("type").asText(null);
        }
        return Article.builder()
                .id(UUID.randomUUID().toString())
                .headline(headline)
                .description(null)
                .imageUrl(null)
                .authorName(null)
                .webUrl(webUrl)
                .publishedDate(publishedDate)
                .keywords(null)
                .typeOfMaterial(typeOfMaterial)
                .build();
    }
}
