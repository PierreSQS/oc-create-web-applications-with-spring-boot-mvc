package com.pierrot.oc.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.pierrot.oc.annotations.GoodMovie;
import com.pierrot.oc.annotations.Priority;
import com.pierrot.oc.annotations.Rating;

import lombok.Data;

@Data
@GoodMovie
@Entity
@Table(name = "MOVIES")
public class WatchlistItem {
	private static int index;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank( message="Please enter the title")
	private String title;
	
	@Rating
	private String rating;
	
	@Priority
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
