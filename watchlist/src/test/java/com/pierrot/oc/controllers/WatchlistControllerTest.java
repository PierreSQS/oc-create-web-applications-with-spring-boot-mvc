package com.pierrot.oc.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
	void contextLoad() {
		
	}

	@Test
	void testGetWatchList() throws Exception {
		mockMvc.perform(get("/watchlist"))
			.andExpect(view().name("watchlist"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("numberOfMovies","watchlistItems"))
			.andExpect(content().string(containsString("The Godfather")))
			.andDo(print());
	}

}
