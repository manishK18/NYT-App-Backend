package com.manish.NewsAggregator.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.manish.NewsAggregator.util.deserializers.GuardianResponseDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = GuardianResponseDeserializer.class)
public class GuardianQueryData extends QueryData {
}
