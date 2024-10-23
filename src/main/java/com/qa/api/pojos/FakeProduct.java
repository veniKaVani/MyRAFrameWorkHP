package com.qa.api.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FakeProduct {
	
	private Integer id;
	private String title;
	private double price;
	private String description;
	private String category;
	private String image;
	private Rating rating;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Rating{
		private double rate;
		private int count;
		
	}
	

}
