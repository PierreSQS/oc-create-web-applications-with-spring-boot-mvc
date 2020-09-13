package com.pierrot.oc.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {WatchlistController.class})
class WatchlistControllerTest {

	private static final String COMMENTHAS51CHARS = "abcdefghij\\n"
													+ "abcdefghij\\n" 
													+ "abcdefghij\\n" 
													+ "abcdefghij\\n" 
													+ "abcdefghij\\n" ;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testGetWatchList() throws Exception {
		mockMvc.perform(get("/watchlistItemForm"))
			.andExpect(view().name("watchlistItemForm"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("watchlistItem"))
			.andExpect(content().string(containsString("Submit an item")));
//			.andDo(print());
	}
	
	@Test
	void testShowWatchListItemForm() throws Exception {
		mockMvc.perform(get("/watchlist"))
			.andExpect(view().name("watchlist"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("numberOfMovies","watchlistItems"))
			.andExpect(content().string(containsString("The Godfather")));
//			.andDo(print());
	}

	@Test
	void testSubmitWatchListItemFormWithAllFields() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "the Prinz of Zamunda")
				.param("rating", "8.0")
				.param("priority", "HIGH")
				.param("comment", "the coolest Eddie Murphy"))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(model().size(1))
			.andExpect(content().string(containsString("")));
//			.andDo(print());
	}

	@Test
	void testSubmitWatchListItemFormWithoutParams() throws Exception {
		mockMvc.perform(post("/watchlistItemForm"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrors("watchlistItem", "title"))
			.andDo(print());
	}
	
	@Test
	void testSubmitWatchListItemFormWithCommentMoreThan50Chars() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "the Prinz of Zamunda")
				.param("comment", COMMENTHAS51CHARS))
			.andExpect(model().attributeHasFieldErrors("watchlistItem", "comment"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "comment","Size"))
			.andDo(print());
	}
	
	@Test
	void testSubmitWatchListItemFormOnCrossFieldValidation() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "the Prinz of Zamunda")
				.param("rating", "9.0")
				.param("priority", "L"))
			.andExpect(model().hasErrors())// there is no field error (cross-field validation)
			.andDo(print());
	}	
	
	@Test
	void testSubmitWatchListItemFormWithRatingEqual5_0() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Avatar")
				.param("rating", "5.0")
				.param("priority", "L"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "rating", "DecimalMin"))
			.andDo(print());
	}	
	
	@Test
	void testSubmitWatchListItemFormWithRatingEqual10_0() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Avatar")
				.param("rating", "10.0")
				.param("priority", "L"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "rating", "DecimalMax"))
			.andDo(print());
	}	
}
