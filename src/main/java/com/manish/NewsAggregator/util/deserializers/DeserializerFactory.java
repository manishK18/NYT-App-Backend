package com.manish.NewsAggregator.util.deserializers;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.manish.NewsAggregator.model.QueryData;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.manish.NewsAggregator.constants.TimeUtils.DATE_TIME_OUTPUT_PATTERN;

@Slf4j
public abstract class DeserializerFactory extends JsonDeserializer<QueryData> {
  protected String getFormattedDateTime(String dateTimeText, String inputPattern) {
    if (dateTimeText == null || dateTimeText.isEmpty()) return null;
    try {
      SimpleDateFormat format = new SimpleDateFormat(inputPattern);
      Date parsedDate = format.parse(dateTimeText);
      SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_TIME_OUTPUT_PATTERN, Locale.US);
      outputFormat.setTimeZone(TimeZone.getDefault());
      if (parsedDate != null) return outputFormat.format(parsedDate);
      return null;
    } catch (ParseException ex) {
      log.info(ex.getLocalizedMessage());
      return ex.getLocalizedMessage();
    }
  }
}
