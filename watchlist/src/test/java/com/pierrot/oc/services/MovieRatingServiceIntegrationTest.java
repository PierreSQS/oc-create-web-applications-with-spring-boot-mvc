package com.pierrot.oc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MovieRatingServiceIntegrationTest {

	@Autowired
	private MovieRatingService movieRatingServ;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		movieRatingServ = new MovieRatingService();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetRating() {
		String title = "the Sicilian Clan";
		String rating = movieRatingServ.getRating(title);

		assertEquals("7.4", rating);
	}
}
