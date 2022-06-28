package com.kodlamaio.rentACar.business.requests.cars;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {

	private int id;
	private int brandId;
	private int colorId;
	private int cityId;
	
	@Min(50)
	private double dailyPrice;
	private String description;
	private int kilometer;
	private String numberPlate;
}
