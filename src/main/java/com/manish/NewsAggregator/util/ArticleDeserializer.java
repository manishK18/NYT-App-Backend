//package com.manish.NewsAggregator.util;
//
//import com.fasterxml.jackson.core.JacksonException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.manish.NewsAggregator.model.Article;
//import com.manish.NewsAggregator.model.Results;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class ArticleDeserializer extends JsonDeserializer<Results> {
//
//    @Override
//    public Results deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
//            throws IOException, JacksonException {
//        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
//        Iterator<JsonNode> docsIterator = node.get("response").get("docs").iterator();
//        ArrayList<Article> articles = new ArrayList<>();
//        while (docsIterator.hasNext()) {
//            JsonNode resultItem = docsIterator.next();
//            String headline = resultItem.get("headline").get("main").asText(null);
//            String description = resultItem.get("lead_paragraph").asText(null);
//            JsonNode multimedia = resultItem.get("multimedia");
//            String imageUrl = getImageUrl(multimedia);
//            String publishedDate = getFormattedDateTime(resultItem.get("pub_date").asText(null));
//            String keywords = getKeywords(resultItem.get("keywords"));
//            String webUrl = resultItem.get("web_url").asText(null);
//            String authorName = resultItem.get("byline").get("original").asText(null);
//            String typeOfMaterial = null;
//            if (resultItem.hasNonNull("type_of_material")) {
//                typeOfMaterial = resultItem.get("type_of_material").asText(null);
//            }
//            Article article = new Article(
//                    imageUrl,
//                    headline,
//                    description,
//                    publishedDate,
//                    keywords,
//                    webUrl,
//                    authorName,
//                    typeOfMaterial
//            );
//            articles.add(article);
//        }
//
//        return new Results(articles);
//    }
//
//    private String getImageUrl(JsonNode multimedia) {
//        for (JsonNode image : multimedia) {
//            if (image.get("subtype").asText().contentEquals("xlarge")) {
//                return "https://www.nytimes.com/" + image.get("url").asText();
//            }
//        }
//        return null;
//    }
//
//    private String getFormattedDateTime(String dateTimeText) {
//        if (dateTimeText == null || dateTimeText.isEmpty()) return null;
//        try {
//            String DATE_TIME_INPUT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
//            String DATE_TIME_OUTPUT_PATTERN = "yyyy-MM-dd HH:mm a";
//            SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_INPUT_PATTERN);
//            Date parsedDate = format.parse(dateTimeText);
//            SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_TIME_OUTPUT_PATTERN, Locale.US);
//            outputFormat.setTimeZone(TimeZone.getDefault());
//            if (parsedDate != null) return outputFormat.format(parsedDate);
//            return null;
//        } catch (ParseException ex) {
//            // Log exception
//            return null;
//        }
//    }
//
//    private String getKeywords(JsonNode keywords) {
//        int MAX_KEYWORDS_LIMIT = 5;
//        int keywordsIncluded = 0;
//        StringBuilder keywordsText = new StringBuilder();
//        if (keywords.isArray()) {
//            for (JsonNode keyword : keywords) {
//                if (!keywordsText.isEmpty()) keywordsText.append(", ");
//                keywordsText.append(keyword.get("value").asText());
//                if (keywordsIncluded++ >= MAX_KEYWORDS_LIMIT) break;
//            }
//        }
//        return keywordsText.toString();
//    }
//}
