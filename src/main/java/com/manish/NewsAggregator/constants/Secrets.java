package com.manish.NewsAggregator.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Secrets {
    @Value("${nytApiKey}")
    public String nytApiKey;
    @Value("${guardianApiKey}")
    public String guardianApiKey;
}
