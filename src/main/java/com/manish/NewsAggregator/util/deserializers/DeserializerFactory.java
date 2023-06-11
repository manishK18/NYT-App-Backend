package com.manish.NewsAggregator.util.deserializers;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.manish.NewsAggregator.model.Results;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public abstract class DeserializerFactory extends JsonDeserializer<Results> {
    protected String getFormattedDateTime(String dateTimeText) {
        if (dateTimeText == null || dateTimeText.isEmpty()) return null;
        try {
            String DATE_TIME_INPUT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
            String DATE_TIME_OUTPUT_PATTERN = "yyyy-MM-dd HH:mm a";
            SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_INPUT_PATTERN);
            Date parsedDate = format.parse(dateTimeText);
            SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_TIME_OUTPUT_PATTERN, Locale.US);
            outputFormat.setTimeZone(TimeZone.getDefault());
            if (parsedDate != null) return outputFormat.format(parsedDate);
            return null;
        } catch (ParseException ex) {
            // Log exception
            return null;
        }
    }
}
