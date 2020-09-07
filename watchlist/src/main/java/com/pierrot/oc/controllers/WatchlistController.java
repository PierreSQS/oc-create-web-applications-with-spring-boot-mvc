package com.pierrot.oc.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WatchlistController {
	
	@GetMapping("watchlist")
	public ModelAndView getWatchList() {
		
		String viewName = "watchlist";
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("numberOfMovies", "1234");
		
		return new ModelAndView(viewName, model);
		
		
	}

}
