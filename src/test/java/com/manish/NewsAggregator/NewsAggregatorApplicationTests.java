package com.manish.NewsAggregator;

import com.manish.NewsAggregator.repository.ArticleDataRepository;
import com.manish.NewsAggregator.repository.CacheDataRepository;
import com.manish.NewsAggregator.services.CacheDataService;
import org.hibernate.dialect.PostgreSQLDialect;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

	@Test
	public void getSearchResults() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders
						.get("/news/search")
						.queryParam("query", "trump")
						.queryParam("offset", "0")
						.queryParam("limit", "20")
				)
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void getSearchResultsWithOffset() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders
								.get("/news/search")
								.queryParam("query", "trump")
								.queryParam("offset", "20")
								.queryParam("limit", "20")
				)
				.andExpect(status().isOk())
				.andReturn();
	}
}