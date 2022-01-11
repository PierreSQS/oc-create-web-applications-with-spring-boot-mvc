package com.pierrot.oc.controllers;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.pierrot.oc.entities.WatchlistItem;
import com.pierrot.oc.services.WatchlistService;


@WebMvcTest
class WatchlistControllerRevisit {
	
	private static final String CROSS_FIELD_ERROR_MSG = "If a movie is as good as 8 then priority should be at least M";
	private final String PRIORITY_ERROR_MSG = "Please enter M,L or H for priority";
	private final String TITEL_ERROR_MSG = "Please enter the title";
	private final String RATING_ERROR_MSG = "Rating should be a number between 1.0-10.0. Decimals with (.) are accepted";
	private final String COMMENT_ERROR_MSG = "Comment should be maximum 50 characters";
	private final String COMMENTGREATERTHAN50 = "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde";
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	WatchlistService watchlistSrvMock;
	
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void formFieldsShouldBeValild() throws Exception {
		// Given
		WatchlistItem watchlistItem = new WatchlistItem("the Godfather", "8.0", "H", "Marlon Brando is the best");
		List<WatchlistItem> watchItemList = List.of(watchlistItem);
		when(watchlistSrvMock.getWatchlist()).thenReturn(watchItemList);

		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("id", "1");
		paramsMap.add("title", watchlistItem.getTitle());
		paramsMap.add("rating", watchlistItem.getRating());
		paramsMap.add("priority", watchlistItem.getPriority());
		paramsMap.add("comment", watchlistItem.getComment());
		
		// When and then
		mockMvc.perform(post("/watchlistItemForm").params(paramsMap))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("/watchlist"))
					.andExpect(model().hasNoErrors())
					.andExpect(model().attributeExists("watchItemsList"))
					.andDo(print());
	}

	@Test
	void priorityShouldNotBeDifferentThanHLM() throws Exception {
		// Given
		WatchlistItem watchlistItem = new WatchlistItem("the Godfather", "8.0", "Hi", "Priority not Valid!");
		List<WatchlistItem> watchItemList = List.of(watchlistItem);
		when(watchlistSrvMock.getWatchlist()).thenReturn(watchItemList);

		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("id", "1");
		paramsMap.add("title", watchlistItem.getTitle());
		paramsMap.add("rating", watchlistItem.getRating());
		paramsMap.add("priority", watchlistItem.getPriority());
		paramsMap.add("comment", watchlistItem.getComment());
		
		// When and then
		mockMvc.perform(post("/watchlistItemForm").params(paramsMap))
					.andExpect(status().isOk())
					.andExpect(model().hasErrors())
					.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "priority", "Priority"))
					.andExpect(model().attributeDoesNotExist("watchItemsList"))
					.andExpect(content().string(containsStringIgnoringCase(PRIORITY_ERROR_MSG)))
					.andDo(print());
	}


	@Test
	void ratingShouldBeBeetween1And10() throws Exception {
		// Given
		WatchlistItem watchlistItem = new WatchlistItem("The Prinz of Zamunda", "11", "H", "Rating > 11 not Valid!");
		List<WatchlistItem> watchItemList = List.of(watchlistItem);
		when(watchlistSrvMock.getWatchlist()).thenReturn(watchItemList);

		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("id", "1");
		paramsMap.add("title", watchlistItem.getTitle());
		paramsMap.add("rating", watchlistItem.getRating());
		paramsMap.add("priority", watchlistItem.getPriority());
		paramsMap.add("comment", watchlistItem.getComment());
		
		// When and then
		mockMvc.perform(post("/watchlistItemForm").params(paramsMap))
					.andExpect(status().isOk())
					.andExpect(model().hasErrors())
					.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "rating", "Rating"))
					.andExpect(model().attributeDoesNotExist("watchItemsList"))
					.andExpect(content().string(containsStringIgnoringCase(RATING_ERROR_MSG)))
					.andDo(print());
	}

	@Test
	void titleShouldNotBeEmpty() throws Exception {
		// Given
		WatchlistItem watchlistItem = new WatchlistItem("", "8.0", "H", "Empty Title not Valid!");
		List<WatchlistItem> watchItemList = List.of(watchlistItem);
		when(watchlistSrvMock.getWatchlist()).thenReturn(watchItemList);

		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("id", "1");
		paramsMap.add("title", watchlistItem.getTitle());
		paramsMap.add("rating", watchlistItem.getRating());
		paramsMap.add("priority", watchlistItem.getPriority());
		paramsMap.add("comment", watchlistItem.getComment());
		
		// When and then
		mockMvc.perform(post("/watchlistItemForm").params(paramsMap))
					.andExpect(status().isOk())
					.andExpect(model().hasErrors())
					.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "title", "NotBlank"))
					.andExpect(model().attributeDoesNotExist("watchItemsList"))
					.andExpect(content().string(containsStringIgnoringCase(TITEL_ERROR_MSG)))
					.andDo(print());
	}

	@Test
	void commentLengthShouldNotBeGreaterThan50() throws Exception {
		// Given
		WatchlistItem watchlistItem = new WatchlistItem("Live and let Die", "7.0", "M", "Comment length > 50 not Valid!");
		List<WatchlistItem> watchItemList = List.of(watchlistItem);
		when(watchlistSrvMock.getWatchlist()).thenReturn(watchItemList);

		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("id", "1");
		paramsMap.add("title", watchlistItem.getTitle());
		paramsMap.add("rating", watchlistItem.getRating());
		paramsMap.add("priority", watchlistItem.getPriority());
		paramsMap.add("comment", COMMENTGREATERTHAN50);
		
		// When and then
		mockMvc.perform(post("/watchlistItemForm").params(paramsMap))
					.andExpect(status().isOk())
					.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "comment", "Size"))
					.andExpect(model().attributeDoesNotExist("watchItemsList"))
					.andExpect(content().string(containsStringIgnoringCase(COMMENT_ERROR_MSG)))
					.andDo(print());
	}

	@Test
	void testSubmitWatchListItemFormOnCrossFieldValidation() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Le clan des siciliens")
				.param("rating", "9.0")
				.param("priority", "L"))
			.andExpect(status().isOk()) // No Redirection
			.andExpect(model().attributeHasErrors("watchlistItem"))// there is no field error (cross-field validation);
			.andExpect(content().string(containsStringIgnoringCase(CROSS_FIELD_ERROR_MSG)))
			.andDo(print());
	}	
	
	
}
