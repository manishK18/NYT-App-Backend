package com.manish.NewsAggregator.constants;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Secrets {
    public static String nytApiKey = System.getenv("NYT_API_KEY");
    public static String guardianApiKey = System.getenv("GUARDIAN_API_KEY");;
}
