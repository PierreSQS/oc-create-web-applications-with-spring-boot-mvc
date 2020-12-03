package com.pierrot.oc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pierrot.oc.entities.WatchlistItem;
import com.pierrot.oc.exceptions.DuplicateTitleException;
import com.pierrot.oc.repositories.WatchlistRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WatchlistService {
	
	private WatchlistRepository watchlistRepo;
	
	private MovieRatingService movieRatingServ;
	
	// the Autowired annotation is set by default
	public WatchlistService(WatchlistRepository watchlistRepo, MovieRatingService movieRatingServ) {
		super();
		this.watchlistRepo = watchlistRepo;
		this.movieRatingServ = movieRatingServ;
	}
	
	public void addItemOrUpdateWatchlist(WatchlistItem watchlistItem) throws DuplicateTitleException {
		WatchlistItem existingItem = watchlistRepo.findById(watchlistItem.getId()).orElse(null);

		if (existingItem == null) {
			
			if ( !watchlistRepo.findByTitle(watchlistItem.getTitle()).isEmpty()) {
//				errors.rejectValue("title", "DuPTitel", "Watchitem with the same Titel already exists");
				throw new DuplicateTitleException("Item with the same Titel already exists!!!");
			}

			// initializing the data
			watchlistRepo.save(watchlistItem);
		} else {
			existingItem.setTitle(watchlistItem.getTitle());
			existingItem.setRating(watchlistItem.getRating());
			existingItem.setPriority(watchlistItem.getPriority());
			existingItem.setComment(watchlistItem.getComment());
		}
	}
	
	public WatchlistItem createItemOnListOrGetItemByIdFromList(Integer id) {
		
		if (id == null) {
			return new WatchlistItem();
		} else {
			return watchlistRepo.findById(id).orElse(new WatchlistItem());
		}
	}
	
	public List<WatchlistItem> getWatchlist() {
		
		List<WatchlistItem> itemList = (List<WatchlistItem>) watchlistRepo.findAll();
		itemList.forEach(item -> {
			String rating = movieRatingServ.getRating(item.getTitle());
			log.info("the Online-Rating: {}", rating);
			if (!rating.isEmpty() & rating != null ) {
				item.setRating(rating);
			}			
		});
		return itemList;
		
	}
	
	public int getWatchlistSize() {
		return (int) watchlistRepo.count();
	}
}
