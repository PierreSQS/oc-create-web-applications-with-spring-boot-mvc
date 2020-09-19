package com.pierrot.oc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pierrot.oc.entities.WatchlistItem;
import com.pierrot.oc.exceptions.DuplicateTitleException;
import com.pierrot.oc.repositories.WatchlistRepository;

@Service
public class WatchlistService {
	private WatchlistRepository watchlistRepo;

	public WatchlistService(WatchlistRepository watchlistRepo) {
		super();
		this.watchlistRepo = watchlistRepo;
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
		return watchlistRepo.getItemList();
		
	}
}
