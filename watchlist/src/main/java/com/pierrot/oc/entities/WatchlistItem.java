package com.pierrot.oc.entities;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.pierrot.oc.validations.GoodMovie;

import lombok.Data;

@Data
@GoodMovie
public class WatchlistItem {
	private static int index;

	private Integer id;
	
	@NotBlank( message="Please enter the title")
	private String title;
	
	@DecimalMin(value = "5", inclusive = false, message ="The rating must be higher than 5.0" )
	@DecimalMax(value = "10", inclusive = false, message ="The rating must be lower than 10.0" )
	private String rating;
	private String priority;	

	@Size(max=50,  message="Comment should be maximum 50 characters")
	private String comment;
	
	public WatchlistItem() {
		this.id = index++;
	}

	public WatchlistItem(String title, String rating, String priority, String comment) {
		this();
		this.title = title;
		this.rating = rating;
		this.priority = priority;
		this.comment = comment;
	}
	
	
}
