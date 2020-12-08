package com.pierrot.oc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MovieRatingServiceIntegrationTest {

	private MovieRatingService movieRatingServ;

		
	static Stream<Arguments> getRatingParams() {
		return Stream.of(
		        Arguments.of("Le clan des siciliens", ""),
		        Arguments.of("The Godfather", "9.2"));
	}
	
	@BeforeEach
	void setUp() throws Exception {
		movieRatingServ = new MovieRatingService();
	}

	@ParameterizedTest
	@MethodSource("getRatingParams")
	void testGetRating(String title, String rating) {
		String onelineRating = movieRatingServ.getRating(title);
		
		assertEquals(rating, onelineRating);
	}
}
