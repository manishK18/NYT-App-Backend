package com.manish.NewsAggregator.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.manish.NewsAggregator.util.deserializers.GuardianResponseDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonDeserialize(using = GuardianResponseDeserializer.class)
public class GuardianResults extends Results{
}
