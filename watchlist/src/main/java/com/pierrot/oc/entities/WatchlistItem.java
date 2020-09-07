package com.pierrot.oc.entities;

import lombok.Data;

@Data
public class WatchlistItem {
	private static int index;

	private Integer Id;
	private String title;
	private String rating;
	private String priority;
	private String comment;
	
	public WatchlistItem() {
		this.Id = index++;
	}

	public WatchlistItem(String title, String rating, String priority, String comment) {
		this();
		this.title = title;
		this.rating = rating;
		this.priority = priority;
		this.comment = comment;
	}
	
	
}
