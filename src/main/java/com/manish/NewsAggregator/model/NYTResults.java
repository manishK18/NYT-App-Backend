package com.manish.NewsAggregator.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.manish.NewsAggregator.util.deserializers.NYTResponseDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonDeserialize(using = NYTResponseDeserializer.class)
public class NYTResults extends Results{
}
