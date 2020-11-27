package com.pierrot.oc.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pierrot.oc.entities.WatchlistItem;

class WatchlistRepositoryTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	private List<WatchlistItem> itemList;

	@BeforeEach
	void setUp() throws Exception {
		itemList = new ArrayList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	// TODO Check why this Test is not working on Maven
	// A workaround is stashed!!
	@Test
	void testCreateOrGetItemById() {
		WatchlistRepository watchlistRepo = new WatchlistRepository(itemList);
		WatchlistItem item = watchlistRepo.createOrGetItemById(null);
		
		WatchlistItem expectedItem = new WatchlistItem();
		expectedItem.setId(4);
		assertEquals(expectedItem  ,item);
	}

}
