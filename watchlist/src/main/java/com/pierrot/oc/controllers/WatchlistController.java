package com.pierrot.oc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pierrot.oc.entities.WatchlistItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WatchlistController {
	private static List<WatchlistItem> watchItemsList = new ArrayList<>();
	
	static {
		// initializing the data of the model
		 watchItemsList.clear();
		 watchItemsList.add(new WatchlistItem("The Godfather", "8.5", "HIGH", "this is a must!"));
		 watchItemsList.add(new WatchlistItem("Le clan des siciliens", "8.0", "HIGH","a french mus"));
		 watchItemsList.add(new WatchlistItem("Live and let die", "8.5", "HIGH", "Kananga is the best!"));
		 watchItemsList.add(new WatchlistItem("Tatort", "2.5", "LOW", "booh to the germans!"));
	}

	@GetMapping("/watchlist")
	public ModelAndView getWatchList() {

		// the view Name
		String viewName = "watchlist";

		Map<String, Object> model = new HashMap<>();

		// fetching the data into the model
		model.put("watchlistItems", watchItemsList);
		model.put("numberOfMovies", watchItemsList.size());

		// Binding the model and the view
		return new ModelAndView(viewName, model);

	}

	@GetMapping("/watchlistItemForm")
	public ModelAndView showWatchListForm(@RequestParam(required = false) Integer id) {
		// the view Name
		String viewName = "watchlistItemForm";
		
		WatchlistItem listItem = createOrGetItemById(id);
		log.info("found List Item: "+listItem);

		// initializing the model and fetching the data into the model
		Map<String, Object> model = new HashMap<>();

		// initializing data with empty object -> empty form
		// and fetching the data into the model
		model.put("watchlistItem", listItem);

		return new ModelAndView(viewName, model);

	}

	@PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchlistItemForm(WatchlistItem watchlistItem) {
		
		WatchlistItem existingItem = findItemById(watchlistItem.getId());

		if (existingItem == null) {
			// initializing the data
			watchItemsList.add(watchlistItem);
		} else {
			// This are functional Errors.
			// BUT THE TESTS ARE STILL WORKING!!!!!!!!
			existingItem.setTitle(watchlistItem.getTitle());
			existingItem.setTitle(watchlistItem.getRating());
			existingItem.setTitle(watchlistItem.getPriority());
			existingItem.setTitle(watchlistItem.getComment());
		}
		// initializing the model and fetching the watchlist Item
		// from the Form into the model
		Map<String, Object> model = new HashMap<>();
		model.put("watchItemsList", watchItemsList);

		// Redirect to /watchlist
		RedirectView redirectView = new RedirectView("/watchlist");

		return new ModelAndView(redirectView, model);

	}

	private WatchlistItem createOrGetItemById(Integer id) {
		return watchItemsList.stream()
				.filter(item -> item.getId().equals(id))
				.findFirst().orElse(new WatchlistItem());
	}
	
	private WatchlistItem findItemById(Integer id) {
		return watchItemsList.stream()
				.filter(item -> item.getId().equals(id))
				.findFirst()
				.orElse(null);
	}
}