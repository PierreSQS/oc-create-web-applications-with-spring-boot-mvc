package com.pierrot.oc.repositories.interfaces;

import java.util.List;

import com.pierrot.oc.entities.WatchlistItem;

public interface WatchlistRepository {

	List<WatchlistItem> getItemList();

	void addItemOnList(WatchlistItem item);

	WatchlistItem createOrGetItemById(Integer id);

	WatchlistItem findItemById(Integer id);

	int getItemListSize();

	boolean isItemByTitelExists(String title);

}