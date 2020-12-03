package com.pierrot.oc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pierrot.oc.entities.WatchlistItem;

public interface WatchlistRepository extends CrudRepository<WatchlistItem, Integer> {
	
	List<WatchlistItem> findByTitle(String title);
	

}
