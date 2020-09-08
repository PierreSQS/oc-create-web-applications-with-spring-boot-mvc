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
	void testSubmitWatchListItemForm() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "the Prinz of Zamunda")
				.param("rating", "8.0")
				.param("priority", "High")
				.param("comment", "the coolest Eddie Murphy"))		
			.andExpect(status().is3xxRedirection())
			.andExpect(model().size(1))
			.andExpect(content().string(containsString("")));
//			.andDo(print());
	}

}
