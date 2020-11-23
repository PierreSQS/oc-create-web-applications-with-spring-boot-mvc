package com.pierrot.oc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pierrot.oc.entities.WatchlistItem;
import com.pierrot.oc.exceptions.DuplicateTitleException;
import com.pierrot.oc.services.WatchlistService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WatchlistController {
	private List<WatchlistItem> watchItemsList;
	
	private WatchlistService watchlistServ;
	
	public WatchlistController(WatchlistService watchlistServ) {
		super();
		this.watchlistServ = watchlistServ;
		watchItemsList = watchlistServ.getWatchlist();
	}

	@GetMapping("/watchlist")
	public ModelAndView getWatchList() {

		// the view Name
		String viewName = "watchlist";

		Map<String, Object> model = new HashMap<>();

		// fetching the data into the model
		model.put("watchlistItems", watchItemsList);
		model.put("numberOfMovies", watchlistServ.getWatchlistSize());

		// Binding the model and the view
		return new ModelAndView(viewName, model);

	}

	@GetMapping("/watchlistItemForm")
	public ModelAndView showWatchListForm(@RequestParam(required = false) Integer id) {
		// the view Name
		String viewName = "watchlistItemForm";
		
		// WHEN SET LIKE BELOW THE TEST PASSES. SO WE MUST SET IT IN THE MOCK!!!!
//		WatchlistItem listItem = new WatchlistItem();
		WatchlistItem listItem = watchlistServ.createItemOnListOrGetItemByIdFromList(id);

		log.info("found List Item: {}",listItem);

		// initializing the model and fetching the data into the model
		Map<String, Object> model = new HashMap<>();

		// initializing data with empty object -> empty form
		// and fetching the data into the model
		model.put("watchlistItem", listItem);

		return new ModelAndView(viewName, model);

	}

	@PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchlistItemForm(@Valid WatchlistItem watchlistItem, Errors errors) {
		
		if (errors.hasErrors()) {
			return new ModelAndView("watchlistItemForm");
		}

		try {
			watchlistServ.addItemOrUpdateWatchlist(watchlistItem);
		} catch (DuplicateTitleException e) {
			errors.rejectValue("title", "DuPTitel", e.getMessage());
			return new ModelAndView("watchlistItemForm");
		}
		// initializing the model and fetching the watchlist Item
		// from the Form into the model
		Map<String, Object> model = new HashMap<>();
		model.put("watchItemsList", watchItemsList);

		// Redirect to /watchlist
		RedirectView redirectView = new RedirectView("/watchlist");

		return new ModelAndView(redirectView, model);

	}
}