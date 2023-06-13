package com.manish.NewsAggregator.constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Secrets {
    @Value("${nytApiKey}")
    public String nytApiKey;
    @Value("${guardianApiKey}")
    public String guardianApiKey;
}
