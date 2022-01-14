package com.pierrot.oc.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.pierrot.oc.entities.WatchlistItem;

@SpringBootTest
@AutoConfigureMockMvc
class WatchlistControllerIT {
	
	private static final String TITEL_ALREADY_EXISTS_ERROR_MSG = "Item with the same Titel already exists!!!";

	@Autowired
	MockMvc mockMvc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	/*
	 * Tests Validation of entering a Movie with an already existing title in the DB (cross-record validation).
	 * In case of a validation error on page, stay on page! Thus no redirection! 
	 * This test can only be executed as an integration test.
	 * The test has been re-written accordingly.
	 * Check the comment in WatchlistControllerTest.testSubmitWatchListItemFormHasNot2EntriesWithSameTitle().
	 * 
	 */	
	@Test
	void testSubmitWatchListItemFormHasNot2EntriesWithSameTitle() throws Exception {
		WatchlistItem formMovieItem = new WatchlistItem("Le clan des siciliens", "8.0", "H", "a french must!");
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", formMovieItem.getTitle())
				.param("rating", formMovieItem.getRating())
				.param("priority", "H")
				.param("comment", "a french must!"))
			.andExpect(status().isOk()) // no redirection since error!!!!
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "title", "DuPTitel")) // (cross-record validation error on title)
			.andExpect(model().attributeErrorCount("watchlistItem", 1))// there is only on error on attribute
			.andExpect(view().name("watchlistItemForm")) // stay on page since no redirection 
			.andExpect(content().string(containsString(TITEL_ALREADY_EXISTS_ERROR_MSG)))// Page contains error message
			.andDo(print());
	}	

}
