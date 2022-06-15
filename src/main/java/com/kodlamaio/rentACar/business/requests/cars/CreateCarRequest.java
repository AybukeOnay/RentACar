package com.kodlamaio.rentACar.business.requests.cars;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCarRequest {
	
	private String description;
	private double dailyPrice;
	private String licensePlate;
	private int kilometer;	
	private int brandId;
	private int colorId;
	private int minFindex;
	

}
