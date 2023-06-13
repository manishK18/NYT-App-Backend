package com.manish.NewsAggregator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewsAggregatorApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private static final String SEARCH_ARTICLE_ENDPOINT = "/news/search";
	private static final String QUERY = "query";
	private static final String OFFSET = "offset";
	private static final String LIMIT = "limit";
	private static final String SAMPLE_QUERY = "trump";

	@Test
	public void getSearchResults() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders
						.get(SEARCH_ARTICLE_ENDPOINT)
						.queryParam(QUERY, SAMPLE_QUERY)
						.queryParam(OFFSET, "0")
						.queryParam(LIMIT, "20")
				)
				.andExpect(status().is2xxSuccessful())
				.andReturn();
	}

	@Test
	public void getSearchResultsWhenWrongLimit() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders
								.get(SEARCH_ARTICLE_ENDPOINT)
								.queryParam(QUERY, SAMPLE_QUERY)
								.queryParam(OFFSET, "0")
								.queryParam(LIMIT, "0")
				)
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void getSearchResultsWithOffset() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders
								.get(SEARCH_ARTICLE_ENDPOINT)
								.queryParam(QUERY, SAMPLE_QUERY)
								.queryParam(OFFSET, "20")
								.queryParam(LIMIT, "20")
				)
				.andExpect(status().isOk())
				.andReturn();
	}
}