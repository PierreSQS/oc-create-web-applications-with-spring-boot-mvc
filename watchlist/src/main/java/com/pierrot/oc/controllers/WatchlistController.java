package com.pierrot.oc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pierrot.oc.entities.WatchlistItem;

@Controller
public class WatchlistController {
	private List<WatchlistItem> watchItemsList;

	@GetMapping("/watchlist")
	public ModelAndView getWatchList() {

		// the view Name
		String viewName = "watchlist";

		watchItemsList = new ArrayList<>();

		// initializing the data of the model
		// watchItemsList.clear();
		// watchItemsList.add(new WatchlistItem("The Godfather", "8.5", "high", "this is
		// a must!"));
		// watchItemsList.add(new WatchlistItem("Le clan des siciliens", "8.0", "high",
		// "a french mus"));
		// watchItemsList.add(new WatchlistItem("Live and let die", "8.5", "high", "this
		// is a must!"));
		// watchItemsList.add(new WatchlistItem("Tatort", "2.5", "high", "booh to the
		// germans!"));

		Map<String, Object> model = new HashMap<>();

		// fetching the data into the model
		model.put("watchlistItems", watchItemsList);
		model.put("numberOfMovies", watchItemsList.size());

		// Binding the model and the view
		return new ModelAndView(viewName, model);

	}

	@GetMapping("/watchlistItemForm")
	public ModelAndView showWatchListForm() {
		// the view Name
		String viewName = "watchlistItemForm";

		// initializing the model and the data
		Map<String, Object> model = new HashMap<>();

		// initializing data with empty object -> empty form
		model.put("watchlistItem", new WatchlistItem());

		return new ModelAndView(viewName, model);

	}

	@PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchlistItemForm(WatchlistItem watchlistItem) {

		// initializing the data
		watchItemsList.add(watchlistItem);

		// initializing the model
		Map<String, Object> model = new HashMap<>();
		model.put("watchItemsList", watchItemsList);

		// Redirect to /watchlist
		RedirectView redirectView = new RedirectView("/watchlist");

		return new ModelAndView(redirectView, model);

	}
}