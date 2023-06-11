package com.manish.NewsAggregator.util;

import com.manish.NewsAggregator.constants.NetworkConstants;
import lombok.Builder;

@Builder
public class URLBuilder {
  private String baseUrl;
  private String endPoint;
  private String query;
  private int pageNum;
  private String apiKey;

  @Override
  public String toString() {
    return baseUrl + endPoint + "?" +
            NetworkConstants.KEY_QUERY + "=" + query + "&" +
            NetworkConstants.KEY_API_KEY + "=" + apiKey + "&" +
            NetworkConstants.KEY_PAGE_NUM + "=" + pageNum;
  }
}
