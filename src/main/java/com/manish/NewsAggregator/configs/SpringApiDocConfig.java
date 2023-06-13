package com.manish.NewsAggregator.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringApiDocConfig {
  private static final String API_DOC_DESC = "Official documentation for the NewsApp API";

  @Autowired
  BuildProperties buildProperties;

  @Bean
  public OpenAPI api(){
    return new OpenAPI()
            .info(getApiInfo());
  }

  private Info getApiInfo() {
    return new Info()
            .title(buildProperties.getName())
            .description(API_DOC_DESC)
            .version(buildProperties.getVersion());
  }
}