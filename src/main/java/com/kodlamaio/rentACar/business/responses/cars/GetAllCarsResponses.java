package com.kodlamaio.rentACar.business.responses.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCarsResponses {

	private int id;
	private int brandId;
	private int colorId;
	private int cityId;
	
	private double dailyPrice;
	private String description;
	private int kilometer;
	private String numberPlate;
}
