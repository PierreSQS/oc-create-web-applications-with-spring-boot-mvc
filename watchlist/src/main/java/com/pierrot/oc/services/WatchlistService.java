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
		WatchlistItem existingItem = watchlistRepo.findItemById(watchlistItem.getId());

		if (existingItem == null) {
			
			if (watchlistRepo.isItemByTitelExists(watchlistItem.getTitle())) {
//				errors.rejectValue("title", "DuPTitel", "Watchitem with the same Titel already exists");
				throw new DuplicateTitleException("Item with the same Titel already exists!!!");
			}

			// initializing the data
			watchlistRepo.addItemOnList(watchlistItem);
		} else {
			existingItem.setTitle(watchlistItem.getTitle());
			existingItem.setRating(watchlistItem.getRating());
			existingItem.setPriority(watchlistItem.getPriority());
			existingItem.setComment(watchlistItem.getComment());
		}
	}
	
	public WatchlistItem createItemOnListOrGetItemByIdFromList(Integer id) {
		return watchlistRepo.createOrGetItemById(id);
	}
	
	public List<WatchlistItem> getWatchlist() {
		
		List<WatchlistItem> itemList = watchlistRepo.getItemList();
		itemList.forEach(item -> {
			String rating = movieRatingServ.getRating(item.getTitle()).trim();
			log.info("the Online-Rating: {}", rating);
			if (rating.isEmpty() || rating != null ) {
				item.setRating(rating);
			}			
		});
		return itemList;
		
	}
	
	public int getWatchlistSize() {
		return watchlistRepo.getItemListSize();
	}
}
