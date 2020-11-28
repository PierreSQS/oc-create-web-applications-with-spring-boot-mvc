package com.pierrot.oc.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pierrot.oc.entities.WatchlistItem;
import com.pierrot.oc.services.WatchlistService;

@WebMvcTest(controllers = {WatchlistController.class})
class WatchlistControllerTest {

	private static final String COMMENTHAS51CHARS = "abcdefghij\\n"
													+ "abcdefghij\\n" 
													+ "abcdefghij\\n" 
													+ "abcdefghij\\n" 
													+ "abcdefghij\\n" ;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	WatchlistService mockWatchlistServ;
	
	@Test
		void testGetWatchList() throws Exception {
			mockMvc.perform(get("/watchlist"))
				.andExpect(view().name("watchlist"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("numberOfMovies","watchlistItems"));
//				.andExpect(content().string(containsString("The Godfather")));
//				.andDo(print());
		}

	// The missing of a Command-Object was causing the Problem of Processing the template
	// No Command-Object was mocked!!
	@Test
	void testShowWatchListItemForm() throws Exception {
		// No Command object was mocked!! We are mocking the Commad Object with an Id=null
		when(mockWatchlistServ.createItemOnListOrGetItemByIdFromList(null)).thenReturn(new WatchlistItem());

		// To make the assertion at line 63 work (model().attribute("watchlistItem", expectedItem))
		// and to prove that the assumption in Commit06 was wrong!!
		WatchlistItem expectedItem = new WatchlistItem();
		expectedItem.setId(0);
		
		mockMvc.perform(get("/watchlistItemForm"))
			.andExpect(status().isOk())
			.andExpect(view().name("watchlistItemForm"))			
			.andExpect(model().attribute("watchlistItem", expectedItem))
			.andExpect(model().attributeExists("watchlistItem"))
			.andDo(print());
	}
	
	@Test
	void testSubmitWatchListItemFormWithAllFields() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "the Prinz of Zamunda")
				.param("rating", "8.0")
				.param("priority", "H")
				.param("comment", "the coolest Eddie Murphy"))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(model().size(1))
			.andExpect(content().string(containsString("")));
//			.andDo(print());
	}

	@Test // tests all Field Validators (e.g all without GoodMovie)
	void testSubmitWatchListItemFormWithoutParams() throws Exception {
		mockMvc.perform(post("/watchlistItemForm"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrors("watchlistItem", "title"))
			.andExpect(model().attributeHasFieldErrors("watchlistItem", "rating"))
			.andExpect(model().attributeHasFieldErrors("watchlistItem", "priority"));
//		    .andDo(print());
	}
	
	@Test
	void testSubmitWatchListItemFormWithCommentMoreThan50Chars() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Tatort")
				.param("priority", "L")
				.param("rating", "2.0")
				.param("comment", COMMENTHAS51CHARS))
			.andExpect(model().attributeHasFieldErrors("watchlistItem", "comment"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "comment","Size"));
//			.andDo(print());
	}
	
	@Test
	void testSubmitWatchListItemFormOnCrossFieldValidation() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Le clan des siciliens")
				.param("rating", "9.0")
				.param("priority", "L"))
			.andExpect(model().attributeHasErrors("watchlistItem"));// there is no field error (cross-field validation);
//			.andDo(print());
	}	
	
	@Test
	void testSubmitWatchListItemFormWithRatingEqual1_0() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Linden Straße")
				.param("rating", "1.0")
				.param("priority", "L"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "rating", "Rating"));
//			.andDo(print());
	}	
	
	@Test
	void testSubmitWatchListItemFormWithRatingEqual10_0() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "The Godfather")
				.param("rating", "10.0")
				.param("priority", "L"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "rating", "Rating"));
//			.andDo(print());
	}	
	
	@Test // priority is missing
	void testSubmitWatchListItemFormWithoutPriority() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Pretty Woman")
				.param("rating", "7.0"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "priority", "Priority"))
			.andExpect(model().attributeErrorCount("watchlistItem", 1)); // priority missing is the only error		
//			.andDo(print());
	}	
	
	@Test // rating wrong, priority wrong 
	void testSubmitWatchListItemFormWitHPriorityEqual_HIGH_Rating_WRONG() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Avatar II")
				.param("rating", "Avatar") // rating must have values 1.0 - 10.0
				.param("priority", "HIGH"))// priority must have one of "H|L|M"
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "rating", "Rating"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "priority", "Priority"))
			.andExpect(model().attributeErrorCount("watchlistItem", 2)); // errors on rating and priority		
//			.andDo(print());
	}	
	
	@Test // rating must be a Number, other fields OK
	void testSubmitWatchListItemFormWitHPriorityEqual_M_Rating_WRONG() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "Avatar III")
				.param("rating", "Avatar") // the only error
				.param("priority", "M"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "rating", "Rating"))
			.andExpect(model().attributeErrorCount("watchlistItem", 1)); // only one error. the other fields OK		
//			.andDo(print());
	}	
	
	@Test // title = null,rating > 10.0, priority != "H", comments > 50chars
	void testSubmitWatchListItemFormCombinationOfValidations() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("rating", "11")
				.param("priority", "HIGH")
				.param("comment", COMMENTHAS51CHARS))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "title", "NotBlank"))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "priority", "Priority"))
			.andExpect(model().attributeErrorCount("watchlistItem", 4)); // GoodMovie Validation + 3 Fields Wrong  		
//			.andDo(print());
	}	
	
	@Test // No title,rating > 10.0, priority and rating OK, comments > 50chars
	void testSubmitWatchListItemFormGoodMoovieNoTitleCommentMoreThan50() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("rating", "9.1")
				.param("priority", "L")
				.param("comment", COMMENTHAS51CHARS))
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "title", "NotBlank"))
			.andExpect(model().attributeErrorCount("watchlistItem", 3)); // GoodMovie Validation + comment > 50chars		
//			.andDo(print());
	}	
	
	// Test Validation of 2 Items with the same title works
	// in case of a validation error on page,
	// stay on page! No redirection!
	@Test
	// this test is no more executable in this form
	// in a mocked context. Thus disabled!!!
	// In this form, same like testSubmitWatchListItemFormWithAllFields
	// Probably must be re-written!!!!! May be the POST-Processing
	// should be better mocked therefore.
	//
	@Disabled 
	void testSubmitWatchListItemFormHasNot2EntriesWithSameTitle() throws Exception {
		mockMvc.perform(post("/watchlistItemForm")
				.param("title", "the Prinz of Zamunda")
				.param("rating", "8.0")
				.param("priority", "H")
				.param("comment", "the coolest Eddie Murphy"))
			.andExpect(status().isOk()) // no redirection
			.andExpect(model().attributeHasFieldErrorCode("watchlistItem", "title", "DuPTitel"))
			.andExpect(model().attributeErrorCount("watchlistItem", 1));// there is no field error (cross-record validation on title);		
//			.andDo(print());
	}	
}
