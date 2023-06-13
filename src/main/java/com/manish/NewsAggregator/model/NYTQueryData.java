package com.manish.NewsAggregator.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.manish.NewsAggregator.util.deserializers.NYTResponseDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = NYTResponseDeserializer.class)
public class NYTQueryData extends QueryData {
}
