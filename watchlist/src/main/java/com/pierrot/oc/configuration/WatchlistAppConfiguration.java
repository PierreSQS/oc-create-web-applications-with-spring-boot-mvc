package com.pierrot.oc.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pierrot.oc.entities.WatchlistItem;
import com.pierrot.oc.repositories.impl.WatchlistRepositoryImpl;
import com.pierrot.oc.repositories.interfaces.WatchlistRepository;
import com.pierrot.oc.services.MovieRatingService;
import com.pierrot.oc.services.WatchlistService;

@Configuration
public class WatchlistAppConfiguration {
	
	private List<WatchlistItem> itemList;
	
	@Autowired
	private WatchlistRepository watchlistRepo;

	@Autowired
	private MovieRatingService movieRatingServ;
	
	@Bean
	public WatchlistRepository createWatchlistRepo() {
		itemList = new ArrayList<>();
		return new WatchlistRepositoryImpl(itemList);
	}
	
	@Bean
	public MovieRatingService createMovieRatingService() {
		return new MovieRatingService();
	}
	
	@Bean
	public WatchlistService createWatchlistService() {
		return new WatchlistService(watchlistRepo, movieRatingServ);
	}	

}
