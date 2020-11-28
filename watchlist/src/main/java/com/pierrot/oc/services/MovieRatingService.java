package com.pierrot.oc.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieRatingService {

	private static final String MYOMDBKEY = "104c4ee4";

	private String apiUrl = "http://www.omdbapi.com/?apikey="+ MYOMDBKEY+"&t=";

	public String getRating(String title) {

		try {
			RestTemplate restTemp = new RestTemplate();

			ResponseEntity<ObjectNode> entity = restTemp.getForEntity(apiUrl+title, ObjectNode.class);
			ObjectNode jsonNode = entity.getBody();
			return jsonNode.path("imdbRating").asText();

		} catch (Exception e) {
			log.info("Something went wront while calling OMDb API {}", e.getMessage());
			return null;
		}

	}
}
