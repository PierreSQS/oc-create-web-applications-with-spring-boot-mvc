package com.pierrot.oc.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pierrot.oc.entities.WatchlistItem;
import com.pierrot.oc.exceptions.DuplicateTitleException;
import com.pierrot.oc.repositories.WatchlistRepository;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceTests {
	
	@Mock
	private WatchlistRepository watchlistRepoMock;
	
	@InjectMocks
	private WatchlistService watchlistServ;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetWatchlistItemsReturnsAllItemsFromRepository() {
		// Arrange
		WatchlistItem item1 = new WatchlistItem("the Godfather", "9.1", "H", "Marlon Brando is the best!");
		WatchlistItem item2 = new WatchlistItem("the Prinz of Zamunda", "8.1", "H", "Eddy Murphy is the one!");
		WatchlistItem item3 = new WatchlistItem("Tatort", "4.1", "L", "Just forget!");
		
		List<WatchlistItem> mockItems = Arrays.asList(item1, item2, item3);		
		when(watchlistRepoMock.getItemList()).thenReturn(mockItems);
		
		// Act
		List<WatchlistItem> results = watchlistServ.getWatchlist();
		
		// Assert
		assertEquals(3,results.size());
		assertEquals("the Godfather", results.get(0).getTitle());
		assertEquals("Tatort", results.get(2).getTitle());
	}
	
	@Test
	void testAddItemOrUpdateWatchlist() throws DuplicateTitleException {
		// Arrange
		WatchlistItem item1 = new WatchlistItem("the Godfather", "9.1", "H", "Marlon Brando is the best!");
		when(watchlistRepoMock.findItemById(item1.getId())).thenReturn(null);
		when(watchlistRepoMock.isItemByTitelExists(item1.getTitle())).thenReturn(false);
		
		// Act
		watchlistServ.addItemOrUpdateWatchlist(item1);
		
		// Assert
		verify(watchlistRepoMock).addItemOnList(item1);		
	}

}
