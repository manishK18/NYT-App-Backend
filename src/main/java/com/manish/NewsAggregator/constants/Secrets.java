package com.manish.NewsAggregator.constants;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Secrets {
    @Value("${nytApiKey}")
    private String nytApiKey;
    @Value("${guardianApiKey}")
    private String guardianApiKey;
}
