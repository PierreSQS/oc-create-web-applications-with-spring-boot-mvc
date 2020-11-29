package com.pierrot.oc.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pierrot.oc.entities.WatchlistItem;
import com.pierrot.oc.repositories.interfaces.WatchlistRepository;

@Repository
public class WatchlistRepositoryImpl implements WatchlistRepository {
	
	private List<WatchlistItem> itemList;
	
	
	public WatchlistRepositoryImpl(List<WatchlistItem> itemList) {
		super();
		this.itemList = itemList;
		initializeList();
	}
	
	@Override
	public List<WatchlistItem> getItemList() {
		return itemList;
	}

	private void initializeList() {
		// initializing the data of the model
		itemList.clear();
		itemList.add(new WatchlistItem("The Godfather", "8.5", "H", "the best film of the world!"));
		itemList.add(new WatchlistItem("Le clan des siciliens", "8.0", "H", "a french must!"));
		itemList.add(new WatchlistItem("Live and let die", "8.5", "H", "Kananga is the best!"));
		itemList.add(new WatchlistItem("Tatort", "2.5", "L", "booh to the germans!"));

	}
	
	@Override
	public void addItemOnList(WatchlistItem item) {
		itemList.add(item);
	}

	@Override
	public WatchlistItem createOrGetItemById(Integer id) {
		return itemList.stream()
				.filter(item -> item.getId().equals(id))
				.findFirst()
				.orElse(new WatchlistItem());
	}
	
	@Override
	public WatchlistItem findItemById(Integer id) {
		return itemList.stream()
				.filter(item -> item.getId().equals(id))
				.findFirst()
				.orElse(null);
	}
	
	@Override
	public boolean isItemByTitelExists(String title) {
		return itemList.stream()
				.anyMatch(item -> item.getTitle()
				.trim().equals(title.trim()));
	}
	
	@Override
	public int getItemListSize() {
		return itemList.size();
	}

}
